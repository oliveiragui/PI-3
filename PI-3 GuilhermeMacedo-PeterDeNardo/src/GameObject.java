public interface GameObject {

    /**
     * O metodo é invocado sempre que possível.
     *
     * @param renderer utilizamos a classe RenderHandler, que é a classe
     *                 que renderiza todos os pixels, sprites, etc
     *                 na tela, com isso, podemos passar os sprites
     *                 de cada GameObject nos metodos dessa classe
     *
     * @param xZoom Multiplica a proximidade do pixel na posição X
     *
     * @param yZoom Multiplica a proximidade do pixel na posição Y
     */
    public void render(RenderHandler renderer, int xZoom, int yZoom);

    /**
     * O metodo é invocado em taxa constante de 60 FPS
     *
     * @param game conseguimos atualizar e receber alguns metodos e
     *             atributos da classe Game para aplicar na lógica
     *             do GameObject nas atualizações do jogo
     */
    public void update(Game game);

    /**
     * A chamada é feita sempre que o mouse for clicado no canvas,
     * cada objeto que implementa esta interface lida com o metódo
     * de maneira diferente
     *
     * @return true quando necessário parar de verificar outros cliques.
     */
    public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom);

    /**
     * @return a layer do GameObject (quando o objeto sobrepõe, ou não, outros objetos na tela)
     */
    public int getLayer();

    /**
     * @return uma instância da classe Rectangle, que indica altura, largura, e posição X,Y do objeto na tela
     */
    public Rectangle getRectangle();
}