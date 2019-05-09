package swing;

public class Enemy extends Sprite {


    public Enemy(int x, int y){
        super(x, y);
        initEnemy();
    }

    public void initEnemy(){
        loadImage("PI/src/resources/link-attack1-S.png");
        getImageDimensions();
    }

}
