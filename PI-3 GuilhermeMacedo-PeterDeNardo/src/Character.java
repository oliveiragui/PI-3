public class Character implements GameObject
{
    /**
     * Objeto Rectangle com as posições, altura, e largura do Player
     */
    private Rectangle characterRectangle;
    /**
     * Objeto Rectangle com as posições, altura, e largura da hitbox do Player
     */
    private Rectangle collisionCheckRectangle;
    /**
     * Velocidade de movimentação do Player
     */
    private int speed = 5;
    /**
     * Direções da animação dos sprites:
     * 0 - Direita
     * 1 - Esquerda
     * 2 - Cima
     * 3 - Baixo
     */
    private int direction = 0;
    /**
     * Layer do sprite do Player
     */
    private int layer = 0;
    /**
     * Objeto Sprite a ser renderizado
     */
    private Sprite sprite;
    /**
     * Objeto AnimatedSprite caso o Player tenha animações
     */
    private AnimatedSprite animatedSprite = null;
    /**
     * Objeto AnimatedSprite caso o Player tenha animações
     */
    private final int xCollisionOffset = 14;
    /**
     * Distanciamento da posição Y do player em ralação a colisão com outros objetos
     */
    private final int yCollisionOffset = 14;

    /**
     * @param sprite Sprite a ser recebido e renderizado
     *
     * @param xZoom Multiplica a proximidade do pixel na posição X
     *
     * @param yZoom Multiplica a proximidade do pixel na posição Y
     */
    public Character(Sprite sprite, int xZoom, int yZoom, int xPos, int yPos)
    {
        this.sprite = sprite;

        if(sprite != null && sprite instanceof AnimatedSprite)
            animatedSprite = (AnimatedSprite) sprite;

        updateDirection();
        characterRectangle = new Rectangle(xPos*(36), yPos*(36), 18, 18);
        characterRectangle.generateGraphics(3, 0xFF00FF90);
        collisionCheckRectangle = new Rectangle(0, 0, 6*xZoom, 11*yZoom);
    }

    /**
     * Atualiza a diração do spriteSheet caso o player tenha animações
     */
    private void updateDirection()
    {
        if(animatedSprite != null)
        {
            animatedSprite.setAnimationRange(direction * 8, (direction * 8) + 7);
        }
    }

    public void render(RenderHandler renderer, int xZoom, int yZoom)
    {
        if(animatedSprite != null)
            renderer.renderSprite(animatedSprite, characterRectangle.x, characterRectangle.y, xZoom, yZoom, false);
        else if(sprite != null)
            renderer.renderSprite(sprite, characterRectangle.x, characterRectangle.y, xZoom, yZoom, false);

    }

    public void update(Game game)
    {
        KeyBoardListener keyListener = game.getKeyListener();

            boolean didMove = false;
            int newDirection = direction;

            collisionCheckRectangle.x = characterRectangle.x;
            collisionCheckRectangle.y = characterRectangle.y;


            if(keyListener.left())
            {
                newDirection = 1;
                didMove = true;
                collisionCheckRectangle.x -= speed;
            }
            if(keyListener.right())
            {
                newDirection = 0;
                didMove = true;
                collisionCheckRectangle.x += speed;
            }
            if(keyListener.up())
            {
                collisionCheckRectangle.y -= speed;
                didMove = true;
                newDirection = 2;
            }
            if(keyListener.down())
            {
                newDirection = 3;
                didMove = true;
                collisionCheckRectangle.y += speed;
            }

            if(newDirection != direction)
            {
                direction = newDirection;
                updateDirection();
            }


            if(!didMove) {
                animatedSprite.reset();
            }

            if(didMove) {


                collisionCheckRectangle.x += xCollisionOffset;
                collisionCheckRectangle.y += yCollisionOffset;

                Rectangle axisCheck = new Rectangle(collisionCheckRectangle.x, characterRectangle.y + yCollisionOffset, collisionCheckRectangle.w, collisionCheckRectangle.h);

                if(!game.getMap().checkCollision(axisCheck, layer, game.getXZoom(), game.getYZoom()) &&
                        !game.getMap().checkCollision(axisCheck, layer + 1, game.getXZoom(), game.getYZoom())) {
                    characterRectangle.x = collisionCheckRectangle.x - xCollisionOffset;
                }

                axisCheck.x = characterRectangle.x + xCollisionOffset;
                axisCheck.y = collisionCheckRectangle.y;
                axisCheck.w = collisionCheckRectangle.w;
                axisCheck.h = collisionCheckRectangle.h;

                if(!game.getMap().checkCollision(axisCheck, layer, game.getXZoom(), game.getYZoom()) &&
                        !game.getMap().checkCollision(axisCheck, layer + 1, game.getXZoom(), game.getYZoom())) {
                    characterRectangle.y = collisionCheckRectangle.y - yCollisionOffset;
                }


                animatedSprite.update(game);
            }

            updateCamera(game.getRenderer().getCamera());

    }

    /**
     * atualiza o player juntamente com a camera, centralizando o sprite
     *
     * @param camera utiliza a posição da camera para atualizar a posição do player
     */
    public void updateCamera(Rectangle camera) {
        camera.x = characterRectangle.x - (camera.w / 2);
        camera.y = characterRectangle.y - (camera.h / 2);
    }

    public int getLayer() {
        return layer;
    }

    public Rectangle getRectangle() {
        return characterRectangle;
    }

    public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) { return false; }
}