public class BycicleAdapter implements Vehicule{
	private Bycicle bycicle;
	
	public BycicleAdapter(Bycicle bycicle) {
		this.bycicle = bycicle;
	}
	@Override
	public void accelerate() {
		bycicle.pedal();
	}
	@Override
	public void sound() {
		bycicle.horn();
	}
}