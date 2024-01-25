public class Car implements Vehicule{
	@Override
	public void accelerate() {
		System.out.println("Speeeed");
	}
	@Override
	public void sound() {
		System.out.println("Bip Bip");
	}
}