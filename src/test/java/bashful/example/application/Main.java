package bashful.example.application;

import bashful.Bashing;
import bashful.annotations.Bash;

@Bash("fortune | cowsay")
public class Main {
	public static void main(String[] args) {
		System.out.println(Bashing.output);
	}
}
