import java.util.Random;

public class Cliente implements Runnable {
  public Cabelereiro cabelereiro;
  public String nome;

  public Cliente(Cabelereiro cabelereiro, String nome) {
    this.cabelereiro = cabelereiro;
    this.nome = nome;
  }

  public synchronized void run() {
    while (cabelereiro.ocupado) {
      try {
        this.wait();
      } catch (InterruptedException e) {}
    }
    System.out.println("O cabelo de " + this.nome + " foi cortado.");
  }

  public synchronized void stopWait() {
    this.notifyAll();
  }
}
