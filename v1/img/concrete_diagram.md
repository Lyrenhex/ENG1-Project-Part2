@startuml
skinparam groupInheritance 3
package BoatPackage <<frame>>{
abstract Boat{
GameController controller
float HP
float speed
float turnSpeed

    abstract void Update()
    void Move(int multiplier) -- Move the boat, multiplier = 1: forwards, multiplier = -1: backwards
    void Turn(float multiplier) -- Turn the boat, positive multiplier turns clockwise, negative turns anti-clockwise
    abstract void Shoot()
    bool IsColliding(PhysicsObject)
    abstract void Destroy()
}

note left of Boat::Move
this will be a method which provides
generic ability to change the boat's position
intended to be used by child classes to implement
their own movement functionality
endnote

class PlayerBoat{
void Update() -- Player controls handled here
void Shoot() -- Player will have different shooting behaviour depending on level/upgrades etc.
void Destroy() -- Player destroyed, call game over function in GameController
}

note left of PlayerBoat::Move
this uses polymorphism to override 
the generic move method to allow for
player controls
endnote

abstract class AiBoat{
	void Move()
	void Shoot()
}

note right of AiBoat 
A class which is implemented
to allow for future ai boats 
to implemented easily (enemies in part 2)
end note

class NeutralBoat{
	void PickRandomMove()
}

note right of NeutralBoat
Boats that just move around randomly
and avoid any obstacles
end note

PhysicsObject <|-- Boat
Boat <|-- PlayerBoat
Boat <|-- AiBoat
AiBoat <|-- NeutralBoat

}
@enduml
