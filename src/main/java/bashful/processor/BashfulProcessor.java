package bashful.processor;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import bashful.annotations.Bash;

@SupportedAnnotationTypes("bashful.annotations.Bash")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class BashfulProcessor extends AbstractProcessor {
	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		try (Writer writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream("C:/Users/OriRoth/Desktop/x.txt"), "utf-8"))) {
			writer.append("############\n");
			for (TypeElement t : annotations)
				writer.append(t.toString() + "\n");
		} catch (IOException e) {
			//
		}
		Runtime rt = Runtime.getRuntime();
		for (Element e : roundEnv.getElementsAnnotatedWith(Bash.class)) {
			Bash a = e.getAnnotation(Bash.class);
			try {
				rt.exec(a.value());
			} catch (IOException e1) {
				//
			}
		}
		return false;
	}
}
