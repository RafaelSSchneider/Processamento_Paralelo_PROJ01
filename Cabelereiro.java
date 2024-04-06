import java.util.Queue;
import java.util.LinkedList;

public class Cabelereiro implements Runnable {
  public boolean ocupado;
  private Queue<Cliente> clientes;

  public Cabelereiro() {
    this.ocupado = false;
    this.clientes = new LinkedList<Cliente>();
  }

  public void addConsumer(Cliente consumer) {
    this.clientes.add(consumer);
  }

  public synchronized void run() {
    this.ocupado = true;
    while(true) {
      Cliente cliente = clientes.poll();

      if (cliente != null) {
        cortarCabelo(cliente);
      } else {
        System.out.println("Nenhum cliente.");
        try {
          Thread.sleep(3000);
        } catch (InterruptedException e) {}
      }
    }
  }

  public synchronized void cortarCabelo(Cliente cliente) {
    System.out.println("Cortando o cabelo de " + cliente.nome + " em 5 segundos");

    try {
      Thread.sleep(1000);
      System.out.println("1..");
      Thread.sleep(1000);
      System.out.println("2..");
      Thread.sleep(1000);
      System.out.println("3..");
      Thread.sleep(1000);
      System.out.println("4..");
      Thread.sleep(1000);
      System.out.println("5..");
    } catch (InterruptedException e) {}

    this.ocupado = false;

    cliente.stopWait();

    try {
      Thread.sleep(100);
    } catch(InterruptedException e) {}
  }
}
