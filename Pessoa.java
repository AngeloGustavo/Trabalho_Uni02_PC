import java.util.Random;

enum Pessoa{
    HOMEM("Homem"), MULHER("Mulher");
    String genero;
    int id;

    Pessoa(String genero) {
        this.genero = genero;
        Random gerador = new Random();
        id = gerador.nextInt(100);
    }

    void setId(int id){
        this.id = id;
    }
}