package bashful.example.application;

import bashful.Bashing;
import bashful.annotations.Bash;

// https://bugs.eclipse.org/bugs/show_bug.cgi?id=350378
@Bash(value = "cowsay moo", name = "Bashing")
public class Main {
	public static void main(String[] args) {
		System.out.println(Bashing.output);
	}
}
