package apigen.gen.tom.java;

import java.io.File;

import apigen.adt.ADT;
import apigen.adt.api.types.Module;
import apigen.gen.tom.TomSignatureGenerator;
import apigen.gen.tom.TomSignatureImplementation;

public class JavaTomSignatureGenerator extends TomSignatureGenerator {

	private String packageName;
	
	public JavaTomSignatureGenerator(ADT adt, TomSignatureImplementation impl, JavaTomGenerationParameters params, Module module) {
		super(adt,  impl, params, module);
		this.packageName = params.getPackageName();
	}
	
    public String getDirectory() {
      String pkg = getPackageName();
      String dir = super.getDirectory();

      if (pkg != null) {
          dir =
              dir
                  + File.separatorChar
                  + getPackageName().replace('.', File.separatorChar);
      }
      return dir;
  }
	
	public String getPackageName() {
    return packageName/*.toLowerCase()*/;
	}
}
