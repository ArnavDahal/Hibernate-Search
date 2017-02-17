import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.PostfixExpression.Operator;
import org.eclipse.jdt.core.dom.*;
import org.apache.commons.io.FileUtils;

public class main {

	// Counters 
	static int dOperators;
	static int dOperands;
	static int tOperators;
	static int tOperands;

	// Arrays to hold all instances of visited nodes
	static ArrayList<String> orNames;
	static ArrayList<String> andNames;

	// Parses the strings passed in by the parseFolder method
	public static void parse(String str) {

		// Inits the string arrays
		orNames = new ArrayList<String>();
		andNames = new ArrayList<String>();

		// Inits the ASTParser
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setResolveBindings(true);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setBindingsRecovery(true);
		Map options = JavaCore.getOptions();
		parser.setCompilerOptions(options);
		parser.setSource(str.toCharArray());

		// The AST Parser
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		// Class for the AST parser
		cu.accept(new ASTVisitor() {

			/** Operators **/

			public boolean visit(VariableDeclarationFragment node) {
				ASTNode aParent = node.getParent(); // Gets the parent node
				String p = aParent.toString(); // Gets the name of the parent node
				String name = p.substring(0, p.indexOf(' ')); // Get the variable type
				orNames.add(name); // Adds the variable type to the string array
				tOperators++; // Increments the counter
				return true;
			}

			public boolean visit(SingleVariableDeclaration node) {

				Type nType = node.getType(); // Gets the node type
				String name = nType.toString();
				orNames.add(name);
				tOperators++;
				return true;
			}

			public boolean visit(InfixExpression node) {
				// Gets the operator
				org.eclipse.jdt.core.dom.InfixExpression.Operator name = node.getOperator();
				orNames.add(name.toString());

				tOperators++;
				return true;
			}

			public boolean visit(Assignment node) {
				org.eclipse.jdt.core.dom.Assignment.Operator name = node.getOperator();
				orNames.add(name.toString());
				tOperators++;
				return true;
			}

			public boolean visit(PostfixExpression node) {
				Operator name = node.getOperator();
				orNames.add(name.toString());
				tOperators++;
				return true;
			}

			/** Operands **/

			public boolean visit(NumberLiteral node) {
				String name = node.getToken();
				andNames.add(name);
				tOperands++;
				return true;
			}

			public boolean visit(SimpleName node) {

				String name = node.getFullyQualifiedName();
				andNames.add(name);
				tOperands++;
				return true;
			}

		});

	}

	// Get all the files in the directory and sub directories
	public static void parseFolders() throws IOException {
		String filePath; // Path to the file
		String content; // .java file converted to a string

		File dir = new File("osp"); // Directory where the project is located

		String[] extensions = new String[] { "java" }; // Extension to find, .java in this case

		List<File> files = (List<File>) FileUtils.listFiles(dir, extensions, true);

		for (File file : files) {
			filePath = file.getCanonicalPath(); // Gets the path
			content = new String(Files.readAllBytes(Paths.get(filePath)));
			parse(content); // Calls the ASTParser 
		}
	}

	// Initializes the variables to 0
	public static void initVars() {
		dOperators = 0;
		dOperands = 0;
		tOperators = 0;
		tOperands = 0;

	}

	// Quickly calculate the distinct amount
	// Taken from Piazza
	public static void findDistinct() {
		HashSet<String> uOr = new HashSet<String>(orNames);
		HashSet<String> uAnd = new HashSet<String>(andNames);
		dOperators = uOr.size();
		dOperands = uAnd.size();

	}

	// Used to calculate log base
	public static double log(int x, int base) {
		return (double) (Math.log(x) / Math.log(base));
	}

	// Uses the following reference to calculate halstead complexity
	// https://en.wikipedia.org/wiki/Halstead_complexity_measures
	public static void halsteadComplex() {

		int n1 = dOperators; //number of distinct operators
		int n2 = dOperands; //number of distinct operands
		int tN1 = tOperators; // total number of operators
		int tN2 = tOperands;// total number of operands

		// Calculates all the halstead values	
		int vocab = n1 + n2;
		int length = tN1 + tN2;
		double calcLength = (n1 * log(n1, 2)) + (n2 * log(n2, 2));
		double volume = length * log(vocab, 2);
		double difficulty = ((double) n1 / 2) * ((double) tN2 / n2);
		double effort = volume * difficulty;
		double time = effort / 18;
		double bugs = volume / 3000;

		// Prints out all the values
		System.out.println("Total Operators: " + tOperators);
		System.out.println("Total Operands: " + tOperands);
		System.out.println("Distinct Operators: " + dOperators);
		System.out.println("Distinct Operands: " + dOperands);
		System.out.println("Program vocabulary: " + vocab);
		System.out.println("Program length: " + length);
		System.out.println("Calculated Length: " + calcLength);
		System.out.println("Volume: " + volume);
		System.out.println("Difficulty: " + difficulty);
		System.out.println("Effort: " + effort);
		System.out.println("Time required to program: " + time + " seconds.");
		System.out.println("Number of delivered bugs: " + bugs);

	}

	public static void main(String[] args) throws IOException {
		initVars();
		parseFolders();
		findDistinct();
		halsteadComplex();
	}
}