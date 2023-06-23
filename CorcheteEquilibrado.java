public class CorcheteEquilibrado {
    public static void main(String[] args) {
        // String str = "()[({)]}()";
        // String str = "([]{})[()]()";
        // String str = "{[()]}"; //returns YES
        // String str = "[(])"; //returns NO
        // String str = "[[(())]]"; //returns YES
        String str = "([]{})[({)]()}[]";
        System.out.println("Cadena : " + str);
        System.out.println(isBalanced(str));
    }

    public static String isBalanced(String s) {
        String rpta = "NO";
        if (s.length() % 2 != 0)
            return rpta;
        int i = s.length() / 2;
        while (i > 0) {
            i--;
            int aux = s.indexOf("()");
            if (aux == -1)
                aux = s.indexOf("[]");
            if (aux == -1)
                aux = s.indexOf("{}");
            if (aux != -1)
                s = s.substring(0, aux) + s.substring(aux + 2);
        }
        if (s.length() == 0)
            rpta = "SI";
        return rpta;
    }
}