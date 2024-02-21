public class App {

	public static void main(String[] args) {
		Shape tri = new Triangle();
		Shape tri1 = new Triangle();
		Shape cir = new Circle();
		
		Drawing drawing = new Drawing();
        
        Drawing drawing2 = new Drawing();
        drawing2.add(tri);
        drawing2.add(cir);

		drawing.add(tri1);
		drawing.add(cir);
        drawing.add(drawing2);
		
		drawing.draw("Red");
		
		drawing.clear();
		
		drawing.add(tri);
		drawing.add(cir);
		drawing.draw("Green");
	}

}