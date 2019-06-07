public class testes {

    public static void main(String[] args) {


        int multiplier = 1;
        for (int i = 0; i < 3; i++) {
            multiplier += 2;
        }

        String[] array = new String[multiplier*multiplier];


        int raiz = (int) Math.sqrt(multiplier);

        int yAtual = -1;
        int xAtual = -1;

        for (int i = 0; i < array.length; i++) {
            String y = "" + yAtual;
            for (int j = raiz; j > 0; j--) {
                String x = "," + xAtual;
                array[i] = y + x;
                xAtual += 1;
            }
        }


    }
}
