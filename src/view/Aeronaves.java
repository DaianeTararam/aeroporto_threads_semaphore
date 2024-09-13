// @author Daiane Tararam
// @date 12 de setembro de 2024

package view;
import controller.Aeroporto;
public class Aeronaves {

	public static void main(String[] args) {
		for (int i = 1; i <= 12; i++) {
			Aeroporto aero = new Aeroporto(i);
			aero.start();
		}
	}

}
