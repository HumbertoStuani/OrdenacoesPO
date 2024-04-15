public class NoPilha {
    private int pos;
    private NoPilha prox;

    public void setProx(NoPilha prox) {
        this.prox = prox;
    }

    public NoPilha(int pos) {
        this.pos = pos;
        this.prox = null;
    }

    public int getPos() {
        return pos;
    }

    public NoPilha getProx() {
        return prox;
    }
}

