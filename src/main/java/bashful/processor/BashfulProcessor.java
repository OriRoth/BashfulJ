package bashful.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;

import org.apache.commons.text.StringEscapeUtils;

import bashful.annotations.Bash;
import util.Wrapper;

@SupportedAnnotationTypes("bashful.annotations.Bash")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class BashfulProcessor extends AbstractProcessor {
	private static final String PACKAGE = "bashful";
	private Wrapper wrapper;
	private Set<String> outputNames = new HashSet<>();

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		wrapper = Wrapper.of(processingEnv);
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		if (roundEnv.processingOver())
			return true;
		for (TypeElement t : annotations)
			for (Element e : roundEnv.getElementsAnnotatedWith(t))
				for (Bash b : e.getAnnotationsByType(Bash.class))
					try {
						// TODO: annotation node should be sent here
						process(b, e);
					} catch (Exception x) {
						// TODO: error should be on annotation node
						wrapper.error(e, x);
					}
		return true;
	}

	private void process(Bash b, Element e) throws IOException, InterruptedException {
		String n = b.name();
		if (outputNames.contains(n))
			return;
		outputNames.add(n);
		Runtime rt = Runtime.getRuntime();
		Process p = rt.exec(new String[] { "/bin/bash", "-c", b.value() });
		p.waitFor();
		String input = read(p.getInputStream());
		String error = read(p.getErrorStream());
		StringBuilder clazz = new StringBuilder();
		clazz.append("package " + PACKAGE + ";\n\n") //
				.append("public class " + n + "{\n") //
				.append("\tpublic static final String output = \"" + input + "\";\n") //
				.append("\tpublic static final String error = \"" + error + "\";\n") //
				.append("}\n");
		FileObject f = wrapper.createSource(PACKAGE + "." + n, e);
		Writer w = f.openWriter();
		w.write(clazz.toString());
		w.close();
		p.destroy();
	}

	private String read(InputStream s) throws UnsupportedEncodingException {
		// TODO: encoding should be read from options (?)
		return StringEscapeUtils.escapeJava(String.join("\n",
				new BufferedReader(new InputStreamReader(s, "UTF-8")).lines().collect(Collectors.toList())));
	}
}
