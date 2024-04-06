public class Barbearia {
  public static void main(String[] args) {
    Cabelereiro cabelereiro = new Cabelereiro();

    Cliente eber = new Cliente(cabelereiro, "Éber");
    Cliente rafael = new Cliente(cabelereiro, "Rafael");
    Cliente artur = new Cliente(cabelereiro, "Artur");

    new Thread(cabelereiro, "cabelereiro1").start();

    new Thread(eber, "cliente1").start();
    new Thread(rafael, "cliente2").start();
    new Thread(artur, "cliente3").start();

    cabelereiro.addConsumer(eber);
    cabelereiro.addConsumer(rafael);
    cabelereiro.addConsumer(artur);
  }
}
