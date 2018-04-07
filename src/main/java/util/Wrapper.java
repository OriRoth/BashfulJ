package util;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic.Kind;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

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

	// TODO: check whether works
	public boolean outputExists(String pkg, String clazz) throws IOException {
		return filer.getResource(StandardLocation.SOURCE_OUTPUT, pkg, clazz + ".java") != null;
	}

	public FileObject createSource(CharSequence pkg, String clazz, Element... originatingElements) throws IOException {
		return filer.createResource(StandardLocation.SOURCE_OUTPUT, pkg, clazz + ".java", originatingElements);
	}
}
