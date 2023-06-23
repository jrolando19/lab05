public class AVLTree<T extends Comparable<T>> {

    class Node {
        // No es necesario que Node tenga <T> Ya que esta clase (Node) está inculuida en
        // una clase genérica
        protected T data;
        protected Node left;
        protected Node right;
        protected int fb; // Factor de balance

        public Node(T data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.fb = 0;
        }

        // Para los nuevos nodos hoja (No tienen hijos)
        public Node(T data) {
            // Llamamos al constructor
            this(data, null, null);
        }
    }

    // ATRIBUTO DE LA CLASE AVL
    private Node root;
    private boolean height; // Si pasa a true es cambio de altura, false lo contrario

    // CONSTRUCTORES
    public void AVL() {
        this.root = null;
    }

    // MÉTODOS DEL ÁRBOL

    // Para saber si el árbol está vacío
    public boolean isEmpty() {
        return this.root == null;
    }

    public void insert(T x) throws ItemDuplicated {
        this.height = false; // Por si el nodo no se inserta
        this.root = insertRec(x, this.root);
    }

    private Node insertRec(T x, Node current) throws ItemDuplicated {
        Node res = current;
        if (current == null) {
            // En el caso base cuando hay inserción debemos consultar el cambio de altura
            this.height = true;
            res = new Node(x);
        } else {
            // resC == resultado de la comparación
            int resC = current.data.compareTo(x);
            if (resC == 0)
                throw new ItemDuplicated("El dato " + x + " ya fue insertado antes.");
            if (resC < 0) {
                res.right = insertRec(x, current.right);
                if (this.height) { // Si existe un cambio en la altura
                    switch (res.fb) {// Casos de fb si insertamos por la derecha
                        // En inserción si fb pasa de -1 o 1 a 0 entonces no hay cambio de altura
                        case -1:
                            res.fb = 0;
                            this.height = false;
                            break; // Se cancela el recálculo de altura
                        case 0:
                            res.fb = 1;
                            this.height = true;
                            break; // Seguimos recalculando
                        case 1: // res.fb = 2; debemos balancear el árbol
                            res = balanceToLeft(res);
                            this.height = false; // Cuando hacemos la rotación el árbol debe quedar balanceado
                    }
                }
            } else {
                res.left = insertRec(x, current.left);
                if (this.height) { // Si existe un cambio en la altura
                    switch (res.fb) { // Casos de fb si insertamos por la derecha
                        // En inserción si fb pasa de -1 o 1 a 0 entonces no hay cambio de altura
                        case 1:
                            res.fb = 0;
                            this.height = false;
                            break;// Se cancela el rec�lculo de altura
                        case 0:
                            res.fb = -1;
                            this.height = true;
                            break; // Seguimos recalculando
                        case -1: // res.fb = -2; debemos balancear el árbol
                            res = balanceToRight(res);
                            this.height = false; // Cuando hacemos la rotación el árbol debe quedar balanceado
                    }
                }
            }
        }
        return res;
    }

    // Para balancear a la izquierda (árbol desbalanceado a la derecha)
    private Node balanceToLeft(Node node) {
        Node son = node.right; // Para balancear a la izquierda debemos trabajar con el hijo derecho
        switch (son.fb) {
            case 1: // Rotación simple (Hijo y padre tienden a la derecha)
                // Como serán rotados el fb será 0
                node.fb = 0;
                son.fb = 0;
                node = rotateSL(node);
                break;
            case -1: // Rotación doble (Padre desbalanceado a la derecha, hijo a la izquierda)
                Node grandson = son.left;
                switch (grandson.fb) { // Actualizamos los fb
                    case -1:
                        node.fb = 0;
                        son.fb = -1;
                        break;
                    case 0:
                        node.fb = 0;
                        son.fb = 0;
                        break;
                    case 1:
                        node.fb = 1;
                        son.fb = 0;
                        break;
                }
                grandson.fb = 0; // El nieto queda balanceado
                // Hacemos las rotaciones
                node.right = rotateSR(son);
                node = rotateSL(node);
                break;
        }
        return node;
    }

    // Para balancear a la derecha (árbol desbalanceado a la izquierda)
    private Node balanceToRight(Node node) {
        Node son = node.left; // Para balancear a la derecha debemos trabajar con el hijo izquierdo
        switch (son.fb) {
            case -1: // Rotación simple (Hijo y padre tienden a la izquierda)
                // Como serán rotados el fb será 0
                node.fb = 0;
                son.fb = 0;
                node = rotateSR(node);
                break;
            case 1: // Rotación doble (Padre desbalanceado a la izquierda, hijo a la derecha)
                Node grandson = son.right;
                switch (grandson.fb) { // Actualizamos los fb
                    case 1:
                        node.fb = 0;
                        son.fb = -1;
                        break;
                    case 0:
                        node.fb = 0;
                        son.fb = 0;
                        break;
                    case -1:
                        node.fb = 1;
                        son.fb = 0;
                        break;

                }
                grandson.fb = 0; // El nieto queda balanceado
                // Hacemos las rotaciones
                node.left = rotateSL(son);
                node = rotateSR(node);
                break;
        }
        return node;
    }

    private Node rotateSL(Node node) {
        Node son = node.right;
        node.right = son.left;
        son.left = node;
        node = son;
        return node;
    }

    private Node rotateSR(Node node) {
        Node son = node.left;
        node.left = son.right;
        son.right = node;
        node = son;
        return node;
    }

    // BÚSQUEDA PARA DEVOLVER DATOS
    public T get(T x) throws ItemNotFound {
        Node res = searchNode(x, root);
        if (res == null)
            throw new ItemNotFound("El dato " + x + " no está ...");
        return res.data;
    }

    private Node searchNode(T x, Node n) {
        if (n == null)
            return null;
        else {
            int resC = n.data.compareTo(x);
            if (resC < 0)
                return searchNode(x, n.right);
            else if (resC > 0)
                return searchNode(x, n.left);
            else
                return n;
        }
    }

    public String toString() {
        if (isEmpty())
            return "Arbol Vacio...";
        String str = inOrden(this.root);
        return str;
    }

    private String inOrden(Node current) {
        // IRD
        String str = "";
        if (current.left != null)
            str += inOrden(current.left);
        str += current.data.toString() + "[" + current.fb + "], ";
        if (current.right != null)
            str += inOrden(current.right);
        return str;
    }

}