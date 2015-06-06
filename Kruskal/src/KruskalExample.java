import br.com.etyllica.EtyllicaFrame;
import br.com.etyllica.context.Application;
import br.com.etyllica.graph.KruskalGraphExample;

public class KruskalExample extends EtyllicaFrame {

	private static final long serialVersionUID = 1L;

	public KruskalExample() {
		super(800, 600);
	}

	public static void main(String[] args){
		KruskalExample viewer = new KruskalExample();
		viewer.init();
	}
	
	@Override
	public Application startApplication() {
		initialSetup("../../../../../");
		return new KruskalGraphExample(w,h);
	}

}
