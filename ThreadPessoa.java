class ThreadPessoa implements Runnable {
    public Banheiro banheiro;
    public Pessoa pessoa;
    
    public ThreadPessoa(Banheiro banheiro, Pessoa pessoa){
        this.banheiro = banheiro;
        this.pessoa = pessoa;
    }

    @Override
    public void run() {
        try {
            for(int i=0;i<10;i++) {
                pessoa.setId(i);
                banheiro.ocuparGenero(pessoa);
                banheiro.usarBanheiro(pessoa);
                banheiro.liberarGenero(pessoa);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}