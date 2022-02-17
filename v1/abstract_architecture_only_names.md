@startuml
package "GameScreens" {
class Screen{
}
note right of Screen
Class from libgdx
end note

enum Screens{

}

class Splash implements Screen{

}

class Menu implements Screen {

}

class GameController implements Screen {
   
}

class GameOverScreen implements Screen{
   
}

class GameWinScreen implements Screen{
    
}

}


package "GeneralControl" {
class Game{
}
note right of Game
class from libgdx
end note
class eng1game extends Game{
    
}
}

package "GameGenerics" {
abstract class GameObject{

}

abstract class PhysicsObject extends GameObject {
  
}

enum Upgrades{
    
}

}

package "Level" {
class WaterBackground extends GameObject {
   
}

class GameMap extends GameObject{
    
}
}


package "Projectiles" {
class Projectile extends PhysicsObject{
    
}

class ProjectileData{
   
}

class ProjectileDataHolder {
   
}
}


package "UI"{
class HUD extends GameObject{
    
}
}

package "Boats" {
abstract class Boat extends PhysicsObject{
    
}

class PlayerBoat extends Boat{
    
}


abstract class AIBoat extends Boat{
  
}


class NeutralBoat extends AIBoat{
   
}


}







package "Colleges" {
abstract College extends PhysicsObject{
   
}

class PlayerCollege extends College{
   
}


class EnemyCollege extends College{
  
}


}
@enduml