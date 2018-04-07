package util;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic.Kind;
import javax.tools.FileObject;

public class Wrapper {
	private final Filer filer;
	private final Messager messager;

	private Wrapper(ProcessingEnvironment env) {
		filer = env.getFiler();
		messager = env.getMessager();
	}

	public static Wrapper of(ProcessingEnvironment env) {
		return new Wrapper(env);
	}

	public void note(String message) {
		messager.printMessage(Kind.NOTE, message);
	}

	public void warning(Element e, String message) {
		messager.printMessage(Kind.WARNING, message, e);
	}

	public void error(Element e, String message) {
		messager.printMessage(Kind.ERROR, message, e);
	}

	public void error(Element e, Exception x) {
		messager.printMessage(Kind.ERROR, x.getMessage(), e);
	}

	public FileObject createSource(String name, Element... originatingElements) throws IOException {
		return filer.createSourceFile(name, originatingElements);
	}
}
