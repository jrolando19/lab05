public class TestAvl {

    public static void main(String[] args) {
        AVLTree<Integer> b = new AVLTree<Integer>();

        try {
            b.insert(50);
            b.insert(80);
            b.insert(30);
            b.insert(100);
            b.insert(15);

            System.out.println(b);
            System.out.println("\nUSANDO LA FUNCIÓN GET");
            System.out.println("¿Está el dato 50? " + b.get(50)); // Si aparece el número entonces está el dato
            // Cuando un elemento no está se lanza la excepción
            System.out.println("¿Está el dato 500? " + b.get(500)); // Si aparece el número entonces está el dato

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}