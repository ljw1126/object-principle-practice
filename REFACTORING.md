# 리팩터링 기록

본 문서는 객체지향 설계 원칙에 따른 코드 개선 과정을 기록합니다.

---

## 1. '묻지 말고 시켜라(Tell, Don't Ask)' 원칙 적용

### 문제 상황
- `Game` 클래스가 `Room`의 `position()`을 호출하여 내부 상태인 `Position` 객체를 직접 가져옴.
- 가져온 `Position`을 다시 `Size.indexOf()`에 전달하여 인덱스를 계산하는 방식은 `Game`이 `Room`의 내부 구현(위치 관리 방식)에 강하게 결합되게 만듦.

**수정 전 코드 (`Game.java`)**
```java
private Room[] arrangeRooms(Room ... rooms) {
    Room[] result = new Room[size.area()];
    for(var room : rooms) {
        // Room에게 위치(Position)를 물어본(Ask) 후, 
        // 외부 객체(Size)를 이용해 인덱스를 직접 계산함
        result[size.indexOf(room.position())] = room;
    }
    return result;
}
```

### 해결 방법
- **Room 클래스 수정**: `indexIn(Size size)` 메서드를 추가하여 `Room`이 스스로 자신의 인덱스를 계산하여 반환하도록 함.
- **캡슐화 강화**: 외부에서 더 이상 사용하지 않는 `Room.position()` 메서드를 제거하여 내부 구현을 숨김.
- **Game 클래스 수정**: `arrangeRooms` 메서드에서 `room.position()`을 묻는 대신 `room.indexIn(size)`를 호출하여 결과를 받음.

**수정 후 코드 (`Room.java` / `Game.java`)**
```java
// Room.java: 스스로의 위치 정보를 활용해 인덱스를 계산하여 반환함 (Tell)
public int indexIn(Size size) {
    return size.indexOf(position);
}

// Game.java: Room에게 인덱스를 알려달라고 시킴 (Tell)
private Room[] arrangeRooms(Room ... rooms) {
    Room[] result = new Room[size.area()];
    for(var room : rooms) {
        result[room.indexIn(size)] = room;
    }
    return result;
}
```

### 결과 및 이점
- **결합도 감소**: `Game` 클래스는 `Room`이 위치 정보를 어떻게 관리하는지 알 필요가 없어짐.
- **응집도 향상**: 객체 스스로가 가진 정보를 바탕으로 직접 계산을 수행하게 되어 책임이 명확해짐.
- **유지보수성 향상**: 향후 `Room`의 위치 관리 방식이 변경되어도 `Game` 클래스의 코드를 수정할 필요가 없음.

---

## 2. Size와 Position 간의 '묻지 말고 시켜라' 적용

### 문제 상황
- `Size` 클래스가 `Position`의 `x()`, `y()` getter를 호출하여 위치가 범위 내에 있는지 확인하거나 인덱스를 계산함.
- `Size`가 `Position`의 내부 데이터에 과도하게 의존하는 **기능 욕심(Feature Envy)**이 발생함.
- `Position`의 내부 구현(x, y 좌표 방식)이 노출되어 결합도가 높아짐.

**수정 전 코드 (`Size.java`)**
```java
public boolean contains(Position position) {
    // Position에게 데이터를 물어본 후(Ask), Size가 직접 로직을 수행함
    return position.x() >= 0 && position.x() < width
            && position.y() >= 0 && position.y() < height;
}

public int indexOf(Position position) {
    return position.x() + position.y() * width;
}
```

### 해결 방법
- **Position 클래스 수정**: `isInside(width, height)`와 `toIndex(width)` 메서드를 추가하여 자신의 상태를 기반으로 스스로 계산하도록 함.
- **캡슐화 강화**: 더 이상 필요 없는 `x()`, `y()` getter를 제거함.
- **Size 클래스 수정**: `Position`의 데이터 대신 메서드를 호출하여 결과만 전달받음.

**수정 후 코드 (`Position.java` / `Size.java`)**
```java
// Position.java: 자신의 데이터(x, y)를 사용하는 로직을 스스로 관리함
public boolean isInside(int width, int height) {
    return y >= 0 && y < height && x < width && x >= 0;
}

public int toIndex(int width) {
    return x + y * width;
}

// Size.java: Position에게 결과만 시킴 (Tell)
public boolean contains(Position position) {
    return position.isInside(width, height);
}

public int indexOf(Position position) {
    return position.toIndex(width);
}
```

### 참고: Getter와 기능 욕심
- **Getter의 문제점**: 
    1. **캡슐화 파괴**: 객체의 내부 상태가 외부에 노출되어 변경 시 영향 범위가 넓어짐.
    2. **결합도 증가**: 외부 객체가 해당 객체의 내부 구조에 강하게 묶임.
- **기능 욕심(Feature Envy)**: 하나의 객체가 자신이 가진 정보보다 다른 객체의 정보를 더 많이 사용하여 로직을 수행하는 코드 악취(Code Smell). 이를 데이터를 가진 객체 쪽으로 옮기면(Tell) 설계를 개선할 수 있음.

- **정보 전문가(Information Expert) 패턴**: 
    - **핵심 원칙**: 책임을 수행하는 데 필요한 정보를 가장 많이 알고 있는 객체에게 책임을 할당함.
    - **적용 사례**: `Position`은 자신의 좌표(`x`, `y`) 정보를 가장 잘 아는 객체이므로, 위치 판별(`isInside`)이나 인덱스 변환(`toIndex`)과 같은 책임을 `Position`에게 할당하는 것이 가장 자연스럽고 응집도가 높음.
