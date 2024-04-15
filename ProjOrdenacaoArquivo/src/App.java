public class App {
    public App(){}
    public void executar(){
        Principal p = new Principal();
        p.geraTabela();
       
    }
    public static void main(String args[]){
        App app = new App();
        app.executar();
    }
}