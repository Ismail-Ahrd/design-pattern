public class Client {
	public static void main(String[] args) {
		Vehicule car = new Car();
		car.accelerate();
		car.sound();
		
		Bycicle b = new Bycicle();
		Vehicule bycicle= new BycicleAdapter(b);
		bycicle.accelerate();
		bycicle.sound();
	}
}