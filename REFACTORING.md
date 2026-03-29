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

---

## 3. Sealed Interface를 활용한 데이터와 행동의 분리

### 문제 상황
- `Game` 클래스의 `parseCommand` 메서드에서 입력 문자열을 직접 파싱하고, 그 결과에 따라 로직을 실행하는 책임이 한데 섞여 있음.
- `go {north|south|east|west}`와 같은 복합 명령어를 처리하기 위해 `switch` 문이 중첩되어 가독성이 떨어짐.
- 새로운 명령어가 추가될 때마다 `Game` 클래스의 핵심 로직을 직접 수정해야 하며, 문자열 기반의 분기 처리는 오타에 취약하고 타입 안전성이 낮음.

### 해결 방법
1. **데이터(Command)와 행동(Execution)의 분리**:
   - `Command` 인터페이스와 하위 `record`들을 정의하여, "어떤 명령을 내릴 것인가(What)"라는 **데이터**를 객체로 캡슐화함.
   - 명령어를 분석하는 과정(`parseCommand`)은 `Command` 객체를 생성하는 데만 집중하고, 실제 실행(`executeCommand`)은 생성된 객체를 기반으로 동작하도록 분리함.

2. **Sealed 키워드를 이용한 타입 캡슐화**:
   - `sealed interface Command`를 사용하여 허용된 명령어 하위 타입(`Move`, `Quit`, `Look` 등)을 엄격하게 제한함.
   - 이를 통해 시스템 내에서 유효한 명령어의 집합이 무엇인지 명확하게 정의하고, 외부에서 임의로 명령어를 확장하지 못하도록 보호함.

3. **Java 21 Switch 패턴 매칭 활용**:
   - `executeCommand`에서 `switch` 문을 통해 각 `Command` 타입을 직접 판별함.
   - `sealed` 인터페이스 덕분에 컴파일러가 모든 명령어 케이스를 다루었는지(Exhaustiveness)를 검사할 수 있어, `default` 절 없이도 안전하고 명확한 분기 처리가 가능해짐.

### 리팩터링 후 구조

**Command.java (데이터 정의)**
```java
public sealed interface Command {
    record Move(Direction direction) implements Command {}
    record Look() implements Command {}
    record Quit() implements Command {}
    record Unknown() implements Command {}
    // ...
}
```

**Game.java (행동 실행)**
```java
private void executeCommand(Command command) {
    switch (command) {
        case Command.Move(var direction) -> tryMove(direction);
        case Command.Quit() -> stop();
        case Command.Look() -> showRoom();
        case Command.Help() -> showHelp();
        case Command.Unknown() -> showUnknownCommand();
    }
}
```

### 결과 및 이점
- **가독성 향상**: 중첩된 `switch` 문과 문자열 파싱 로직이 제거되고, 명확한 타입 기반의 선언적 코드로 변경됨.
- **타입 안전성 확보**: 문자열 오타로 인한 런타임 오류가 줄어들고, `record`를 통해 명령어에 필요한 데이터(예: `Direction`)를 안전하게 전달함.
- **유지보수성 증대**: 새로운 명령어 추가 시 `Command` 인터페이스에 하위 타입을 추가하고 `switch` 문에 `case`를 하나 더하는 것으로 충분하며, 컴파일러가 누락된 처리를 알려줌.
- **객체지향과 함수형의 조화**: 객체지향의 캡슐화와 함수형의 대수적 데이터 타입(ADT) 장점을 결합하여, 데이터의 구조를 안전하게 보호하면서도 로직을 깔끔하게 분리할 수 있게 됨.

---

## 4. Switch Expression과 yield를 활용한 방어적 파싱

### 문제 상황
- `CommandParser`에서 사용자의 입력을 공백 단위로 분리(`split`)하여 명령어를 파싱할 때, 특정 명령어(예: `go`) 뒤에 추가 인자(방향)가 없는 경우 `ArrayIndexOutOfBoundsException`이 발생함.
- `switch` 문 내에서 인자의 유효성을 검사하고 결과를 반환해야 하는 상황에서, 단순한 값 반환 이상의 로직(조건문 등)이 필요함.

**수정 전 코드 (`CommandParser.java`)**
```java
private Command parseCommand(String[] commands) {
    return switch (commands[0]) {
        case "go" -> 
            // commands[1] 접근 시 배열 크기가 1이면 예외 발생
            switch (commands[1]) {
                case "north" -> new Command.Move(Direction.NORTH);
                // ...
            };
        // ...
    };
}
```

### 해결 방법
1. **방어적 코드 추가**: `commands.length`를 체크하여 인자가 부족한 경우 즉시 `Command.Unknown()`을 반환하도록 개선함.
2. **Switch 블록과 yield 사용**: `switch` 식(`Expression`)의 각 `case`에서 단순 값이 아닌 **로직 블록(`{ ... }`)**을 사용하고, 최종 결과를 `yield` 키워드로 반환함.

### Java 21의 yield 키워드 이해
- **역할**: `switch` 식 내의 코드 블록에서 최종적으로 반환할 값을 결정함. (일반적인 `return`은 메서드 자체를 종료시키지만, `yield`는 `switch` 식의 결과값만 결정함)
- **사용 시점**:
    - `case -> { ... }`와 같이 화살표 뒤에 **중괄호 블록**을 사용할 때.
    - `case :`와 같은 **전통적인 콜론 문법**을 식(`Expression`)으로 사용할 때.

**수정 후 코드 (`CommandParser.java`)**
```java
private Command parseCommand(String[] commands) {
    // 1. 빈 입력 방어
    if (commands.length == 0 || commands[0].isEmpty()) {
        return new Command.Unknown();
    }

    return switch (commands[0]) {
        case "go" -> {
            // 2. 인자 개수 확인 (방어 로직)
            if (commands.length < 2) {
                yield new Command.Unknown(); // 블록 내 결과 반환
            }
            // 3. 중첩 switch의 결과를 다시 yield로 반환
            yield switch (commands[1]) {
                case "north" -> new Command.Move(Direction.NORTH);
                case "south" -> new Command.Move(Direction.SOUTH);
                case "east" -> new Command.Move(Direction.EAST);
                case "west" -> new Command.Move(Direction.WEST);
                default -> new Command.Unknown();
            };
        }
        case "look" -> new Command.Look();
        case "help" -> new Command.Help();
        case "quit" -> new Command.Quit();
        default -> new Command.Unknown();
    };
}
```

### 결과 및 이점
- **안정성 강화**: 잘못된 입력(예: 단독 "go")에도 프로그램이 중단되지 않고 적절한 에러 커맨드(`Unknown`)를 생성함.
- **표현력 향상**: `switch` 문 안에서 단순 매핑뿐만 아니라 필요한 검증 로직을 자연스럽게 녹여낼 수 있음.
- **명확한 의도**: `yield`를 사용함으로써 "이 블록의 목적은 특정 값을 계산하여 내보내는 것"임을 명시적으로 드러냄.

---

## 5. 디미터의 법칙(Law of Demeter)과 기차 충돌 방지

### 문제 상황
- `Game` 클래스가 현재 위치한 방의 정보를 출력하기 위해 `player.currentRoom().name()`과 같이 객체의 내부 구조를 따라 여러 단계 깊숙이 접근함.
- `Game`은 `Player`뿐만 아니라 `Room` 객체의 메서드까지 호출하게 되어, `Player`의 내부 구현(방 정보를 어떻게 관리하는지)에 강하게 결합됨.

**수정 전 코드 (`Game.java`)**
```java
public void showRoom() {
    // 기차 충돌(Train Wreck) 발생: 여러 개의 도트(.)를 따라 내부 객체를 헤집고 다님
    // Game에게 Room은 '낯선 이(Stranger)'인데 직접 말을 걸고 있음
    var room = player.currentRoom();
    System.out.println("당신은 [" + room.name() + "]에 있습니다.");
}
```

### 해결 방법
1. **위임 메서드(Forwarding Method) 추가**: `Player` 클래스에 `currentRoomName()`, `currentRoomDescription()`과 같은 메서드를 추가하여 정보를 대신 전달하도록 함.
2. **캡슐화 강화**: `Player.currentRoom()` 메서드를 `private`으로 변경하여 외부에서 `Room` 객체(낯선 이)에게 직접 접근할 수 없도록 차단함.

**수정 후 코드 (`Player.java` / `Game.java`)**
```java
// Player.java: 낯선 이(Room)를 감추고 필요한 정보만 제공함
private Room currentRoom() {
    return worldMap.roomAt(position);
}

public String currentRoomName() {
    return currentRoom().name(); // 위임(Forwarding)
}

// Game.java: 오직 친구(Player)에게만 말을 검
public void showRoom() {
    System.out.println("당신은 [" + player.currentRoomName() + "]에 있습니다.");
}
```

### 결과 및 이점
- **기차 충돌(Train Wreck) 해결**: 여러 개의 마침표(`.`)가 연결된 코드가 사라지고 단일 메시지 전송으로 변경되어 가독성이 높아짐.
- **낯선 이(Stranger)와의 작별**: `Game`은 이제 `Room` 객체의 존재를 알 필요가 없으며, 오직 `Player`가 제공하는 인터페이스에만 의존함.
- **결합도 감소**: `Room` 클래스의 메서드명이 변경되더라도 `Player` 내부만 수정하면 되며, `Game` 코드는 영향을 받지 않음.
- **응집도 향상**: "현재 방 정보 제공"이라는 책임을 적절한 전문가(`Player`)에게 맡김으로써 객체 간의 역할 분담이 명확해짐.

### 참고: 트레이드오프와 미들 맨(Middle Man)
- **위임의 트레이드오프**: 디미터의 법칙을 준수하기 위해 위임 메서드(Forwarding Method)를 추가하면 결합도는 낮아지지만, 클래스 내 메서드 수가 늘어나고 코드가 복잡해질 수 있음.
- **미들 맨(Middle Man) 악취**: 클래스의 대부분의 메서드가 단순히 다른 객체로 요청을 전달하기만 한다면, 이는 과도한 위임으로 인한 '미들 맨' 코드 악취에 해당함. 이 경우 위임을 제거하고 직접 소통하는 것이 나을 수도 있음.
- **정보 전문가(Information Expert) 관점**: "현재 내가 어디에 있고, 그곳의 이름은 무엇인가?"라는 질문에 가장 잘 답할 수 있는 객체는 자신의 위치(`Position`)와 지형도(`WorldMap`)를 모두 알고 있는 `Player`임. 따라서 `Player`에게 이 책임을 할당하는 것은 객체 간의 결합도를 낮추는 동시에 응집도를 높이는 올바른 결정임.
