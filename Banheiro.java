import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Banheiro {
    public static void main(String[] args){
        Banheiro banheiro = new Banheiro(5);        
        for(int i=0 ;i<5; i++){
            int valorAleatorio = gerador.nextInt(2);
            if(valorAleatorio == 0)
                new Thread(new ThreadPessoa(banheiro, Pessoa.HOMEM)).start();
            else
                new Thread(new ThreadPessoa(banheiro, Pessoa.MULHER)).start();
        }
    }

    static Random gerador = new Random();
    volatile Pessoa generoNoBanheiro;
    AtomicInteger ocupacaoBanheiro;
    int tamanhoBanheiro;
    Semaphore semaforoDeEntrada;
    Semaphore semaforoDeGenero = new Semaphore(1);

    public Banheiro(int tamanhoBanheiro) {
        this.ocupacaoBanheiro =  new AtomicInteger(0);
        this.tamanhoBanheiro = tamanhoBanheiro;
        this.semaforoDeEntrada = new Semaphore(tamanhoBanheiro);
    }

    void ocuparGenero(Pessoa pessoa) throws InterruptedException {
        if(ocupacaoBanheiro.get() == 0){
            semaforoDeGenero.acquire();
            generoNoBanheiro = pessoa;
        }
        if(!pessoa.genero.equals(generoNoBanheiro.genero)){
            semaforoDeGenero.acquire();
            generoNoBanheiro = pessoa;
        }
    }

    public void liberarGenero(Pessoa pessoa) {
        if(ocupacaoBanheiro.get()==0){
            semaforoDeGenero.release();
            generoNoBanheiro = null;
            if(pessoa.genero == "Homem")
                System.out.println("========= O último homem saiu. =========");
            else
                System.out.println("========= A última mulher saiu. =========");
        }
    }

    public void usarBanheiro(Pessoa pessoa) throws InterruptedException {
        semaforoDeEntrada.acquire();
        ocupacaoBanheiro.incrementAndGet();
        System.out.println(pessoa.genero + "[" + Thread.currentThread().getId()+pessoa.id +"] entrou. (Pessoas no banheiro: "+ocupacaoBanheiro.get()+")");
        Thread.sleep(1000);
        semaforoDeEntrada.release();
        ocupacaoBanheiro.decrementAndGet();
        System.out.println(pessoa.genero + "[" + Thread.currentThread().getId()+pessoa.id + "] saiu. (Pessoas no banheiro: "+ocupacaoBanheiro.get()+")");
    }
}