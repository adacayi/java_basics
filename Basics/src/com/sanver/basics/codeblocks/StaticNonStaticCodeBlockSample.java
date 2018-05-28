package com.sanver.basics.codeblocks;

class Trial {
	{
		System.out.println("Non static block Trial");
	}
	static {
		System.out.println("Selamunaleykum Trial");
	}

	public Trial() {
		System.out.println("Ve aleykumselam Trial");
	}
}

class StaticNonStaticCodeBlockSample {
	{
		System.out.println("Non static block");
	}
	static {
		System.out.println("Selamunaleykum");
	}

	public StaticNonStaticCodeBlockSample() {
		System.out.println("Constructor");
	}

	public static void main(String[] args) {
		System.out.println("Ve aleykumselam");
		System.out.println();
		new StaticNonStaticCodeBlockSample();
		System.out.println();
		new Trial();
		System.out.println();
		new Trial();
	}
}
