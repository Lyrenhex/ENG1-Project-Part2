@startuml
package "GameScreens" {
class Screen{
}
note right of Screen
Class from libgdx
end note

+enum Screens{
    splashScreen
    menuScreen
    gameScreen
    gameOverScreen
    gameWinScreen
}

+class Splash implements Screen{
    - SpriteBatch batch
    - Sprite splash
    - Sound splashSound1
    - Sound splashSound2
    + eng1game game
    - boolean fading
}

+class Menu implements Screen {
    - SpriteBatch batch
    BitmapFont font
    GlyphLayout menuTextLayout
    eng1game game
}

+class GameController implements Screen {
    eng1game game
    GameObject[] objects
    PhysicsObject[] physicsObjects
    + College[] colleges
    float testRot
    - SpriteBatch batch
    GameMap map
    BitmapFont font
    + HUD hud
    + int xp
    + int plunder
    float xpTick
    float xpTickMultiplier
    + ProjectileDataHolder projectileHolder
    + PlayerBoat playerBoat
    - EnemyCollege bossCollege

    + void UpdateObjects(float)
    + void CollegeDestroyed()
    + void ClearKilledObjects()
    + void gameOver
    + void NewPhysicsObject(PhysicsObject)
    + void RemovePhysicsObject(PhysicsObject)
    + void AddXP(int)
}

+class GameOverScreen implements Screen{
    - SpriteBatch batch
    BitmapFont font
    GlyphLayout gameOverTextLayout
    eng1game game
}

+class GameWinScreen implements Screen{
    - SpriteBatch batch
    BitmapFont font
    GlyphLayout winTextLayout
    eng1game game
}

}


package "GeneralControl" {
class Game{
}
  note right of Game
  Class from libgdx
  end note

+class eng1game extends Game{
    SpriteBatch batch;
    Texture img;
    Menu menuScreen;
    GameController gameScreen;

    + void gotoScreen(Screens)
}
}

package "GameGenerics" {
+abstract class GameObject{
    + Vector2 position
    + float rotation
    + Sprite sprite
    + bool killOnNextTick

    + void Update(float, PhysicsObject)
    + void Draw(SpriteBatch)
}

+abstract class PhysicsObject extends GameObject {
    + Polygon collisionPolygon
    + bool CheckCollisionWith(PhysicsObject)
    + {abstract} void OnCollision(PhysicsObject)
}

+enum Upgrades{
    health
    maxhealth
    speed
    turnspeed
    projectiledamage
    projectilespeed
    defense
    + String label
}

}

package "Level" {
+class WaterBackground extends GameObject {
    TextureRegion[] waterTextureRegion
    TextureRegionDrawable waterTextureRegionDrawable
    int waterTextureNumber
    float lastWaterTextureChange
    float waterChangeDelay
    Vector2 screenSize
}

+class GameMap extends GameObject{
    + Ort hographicCamera camera
    PlayerBoat boat
    Vector2 boundaries
    WaterBackground bg
}
}


package "Projectiles" {
+class Projectile extends PhysicsObject{
    - Vector2 velocity
    - float speed
    - Sprite sprite
    + boolean isPlayerProjectile
    + float damage
}

+class ProjectileData{
    + float speed
    + float damage
    + Vector2 size
    + Texture texture
}

+class ProjectileDataHolder {
    + ProjectileData stock
    + ProjectileData enemy
    + ProjectileData boss
}
}


package "UI"{
+class HUD extends GameObject{
    GlyphLayout hpTextLayout
    WaterBackground bg
    Stage stage
    TextButton menuButton
    TextButtonStyle menuButtonStyle
    TextButton upgradeButton1
    TextButtonStyle upgradeButton1Style
    TextButton upgradeButton2
    TextButtonStyle upgradeButton2Style
    Image upgradeMenuBackground
    GlyphLayout xpTextLayout
    GlyphLayout plunderTextLayout
    BitmapFont font
    GameController gc
    + boolean hoveringOverButton 
    boolean upgradeMenuOpen
    boolean upgradeMenuInitialised
    Upgrades upgrade1
    int upgrade1cost
    float upgrade1amount
    Upgrades upgrade2
    int upgrade2cost
    float upgrade2amount

    + void DrawUpgradeButton()
    + void ToggleMenu()
    + void InitialiseMenu()
    + void UpdateMenu()
    void BuyUpgrade(int)
    void RandomiseUpgrades()
}
}

package "Boats" {
+abstract class Boat extends PhysicsObject{
    + int HP
    + int maxHP
    # float speed
    # float turnSpeed
    # float shotDelay
    # float timeSinceLastShot
    # Vector2[] mapBounds
    # Vector2 mapSize
    void Move(float, int)
    void Turn(float, float)
    void SetPosition(float, float)
    {abstract} void Shoot()
    {abstract} void Destroy()
    + float GetCenterX()
    + float GetCenterY()	
}

+class PlayerBoat extends Boat{
    float projectileDamageMultiplier
    float projectileSpeedMultiplier
    int defense
    float timeSinceLastHeal

    + void Upgrade(Upgrades, float)
    + void Heal(int, float)
}


+abstract class AIBoat extends Boat{
    Vector2 initialPosition
    Vector2 destination
    float plunderValue
    float xpValue
    float destinationThreshold
    float angleThreshold

    + void MoveToDestination(float)
    boolean SetDestination(Vector2)
    Vector2 GetDestination()
}
note right of AIBoat 
A class that is implemented
to allow for future ai boats 
to be implemented easily (enemies in part 2)
end note

+class NeutralBoat extends AIBoat{
    + void Update(float)
    + void Destroy()
    + void OnCollision(PhysicsObject)
}

note right of NeutralBoat
Boats that just move around randomly
and avoid any obstacles
end note
}







package "Colleges" {
+abstract College extends PhysicsObject{
    int range
    int HP
    int damage
    int fireRate
    Sprite aliveSprite
    Sprite deadSprite
    Sprite islandSprite

    boolean isInRange(Boat)
}

+class PlayerCollege extends College{
    int healAmount
}
note bottom of PlayerCollege
will repair the player
when they are within
a certain range
end note

+class EnemyCollege extends College{
    int damage
    float shootingInaccuracy
    float fireRate
    float timeSinceLastShot
    Random rd
    GameController gc
    ProjectileData projectileType
    BitmapFont font
    GlyphLayout hpText
    int maxHP
    + int HP
    + boolean invulnerable

    + void Update(float, PhysicsObject)
    + void Draw(SpriteBatch)
    void ShootAt(Vector2)
    + void becomeVulnerable()

}

note bottom of EnemyCollege
will attack the player
when they are within a
range
end note
}
@enduml


