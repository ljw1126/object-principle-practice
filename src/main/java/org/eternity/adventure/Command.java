package org.eternity.adventure;

import org.eternity.adventure.constant.Direction;

/**
 * Java 17 이상에서 도입된 sealed 인터페이스입니다.
 * 이 인터페이스를 상속/구현할 수 있는 클래스를 같은 파일 내부 혹은 permits 절로 제한합니다.
 * 
 * 주요 특징:
 * 1. 타입 계층의 엄격한 제어: 아무나 Command를 구현할 수 없도록 막습니다.
 * 2. switch 패턴 매칭 (Java 21 정식 기능): switch 문에서 모든 구현체(Move, Unknown 등)를 
 *    나열하면 컴파일러가 모든 케이스를 처리했음을 보장하여 default 절이 필요 없습니다.
 */
public sealed interface Command {
    record Move(Direction direction) implements Command {}
    record Unknown() implements Command {}
    record Look() implements Command {}
    record Help() implements Command {}
    record Quit() implements Command {}
    record Inventory() implements Command {}
    record Take(String item) implements Command {}
    record Drop(String item) implements Command {}
}
