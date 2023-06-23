class Node {
    int valor;
    Node izquierda, derecha;

    public Node(int item) {
        valor = item;
        izquierda = derecha = null;
    }
}

class BinaryTree {
    Node raiz;

    BinaryTree() {
        raiz = null;
    }

    void insert(int valor) {
        raiz = insertRec(raiz, valor);
    }

    Node insertRec(Node raiz, int valor) {
        if (raiz == null) {
            raiz = new Node(valor);
            return raiz;
        }

        if (valor < raiz.valor)
            raiz.izquierda = insertRec(raiz.izquierda, valor);
        else if (valor > raiz.valor)
            raiz.derecha = insertRec(raiz.derecha, valor);

        return raiz;
    }

    void delete(int valor) {
        raiz = delete(raiz, valor);
    }

    Node delete(Node raiz, int valor) {
        if (raiz == null)
            return raiz;

        if (valor < raiz.valor)
            raiz.izquierda = delete(raiz.izquierda, valor);
        else if (valor > raiz.valor)
            raiz.derecha = delete(raiz.derecha, valor);
        else {
            if (raiz.izquierda == null)
                return raiz.derecha;
            else if (raiz.derecha == null)
                return raiz.izquierda;

            raiz.valor = minValue(raiz.derecha);
            raiz.derecha = delete(raiz.derecha, raiz.valor);
        }

        return raiz;
    }

    int minValue(Node raiz) {
        int minv = raiz.valor;
        while (raiz.izquierda != null) {
            minv = raiz.izquierda.valor;
            raiz = raiz.izquierda;
        }
        return minv;
    }

    void inorder() {
        inorderRec(raiz);
    }

    void inorderRec(Node raiz) {
        if (raiz != null) {
            inorderRec(raiz.izquierda);
            System.out.print(raiz.valor + " ");
            inorderRec(raiz.derecha);
        }
    }

    public static void main(String[] args) {
        BinaryTree arbol = new BinaryTree();

        arbol.insert(40);
        arbol.insert(20);
        arbol.insert(10);
        arbol.insert(30);
        arbol.insert(50);
        arbol.insert(60);
        arbol.insert(70);

        System.out.println("Árbol original:");
        arbol.inorder();
        System.out.println();
        int valorToDelete = 30;
        arbol.delete(valorToDelete);

        System.out.println("Árbol después de eliminar el nodo con clave " + valorToDelete + ":");
        arbol.inorder();
    }
}