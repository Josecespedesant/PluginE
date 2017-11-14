package pluginproject3.handlers;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.WhileStatement;

import com.sun.javafx.collections.MappingChange.Map;

import listas.simple.Lista;


/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SampleHandler extends AbstractHandler {

	private static final String JDT_NATURE = "org.eclipse.jdt.core.javanature";
	public static ArrayList<String> partesDelMetodo = new ArrayList<String>();
	public ArrayList<Statement> bodCont = new ArrayList<Statement>();
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceRoot root = workspace.getRoot();
        // Get all projects in the workspace
        IProject[] projects = root.getProjects();
        // Loop over all projects
        for (IProject project : projects) {
            try {
                if (project.isNatureEnabled(JDT_NATURE)) {
                    analyseMethods(project);
                }
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void analyseMethods(IProject project) throws JavaModelException {
        IPackageFragment[] packages = JavaCore.create(project)
                .getPackageFragments();
        // parse(JavaCore.create(project));
        for (IPackageFragment mypackage : packages) {
            if (mypackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
                createAST(mypackage);
            }

        }
    }

    private void createAST(IPackageFragment mypackage)
            throws JavaModelException {
        for (ICompilationUnit unit : mypackage.getCompilationUnits()) {
            // now create the AST for the ICompilationUnits
            CompilationUnit parse = parse(unit);
            MethodVisitor visitor = new MethodVisitor();
            parse.accept(visitor);

            for (MethodDeclaration method : visitor.getMethods()) {
            	partesDelMetodo.add(method.getName().toString());
            	
            	lecturaDelMetodo(method); 
            	           	
               
                //System.out.print("Method name: " + method.getName()
                	//	+ method.getName().IF_STATEMENT
                      //  + " Return type: " + method.getReturnType2());
            }

            while(partesDelMetodo.isEmpty() != true) {
        		System.out.print(partesDelMetodo.get(0).toString());
           		partesDelMetodo.remove(0);
        	}
            
        }
    }
    
   public void lecturaDelMetodo(MethodDeclaration metodo) {
	   
	 List statement = metodo.getBody().statements();
	 
	 while(statement.isEmpty() != true) {
		 Object ola=  statement.get(0); 
		 parseoDeElementos(ola);
		 
//		 if(statements.get(0) instanceof IfStatement) {
//			 partesDelMetodo.add("IFS");
//			 IfStatement theStatement = (IfStatement) statements.get(0);
//			 partesDelMetodo.add(theStatement.getExpression().toString());
//			 partesDelMetodo.add(theStatement.getThenStatement().toString());
//		 }
		 statement.remove(0);
		 
	 }
	 
	 
   }

   
   public void parseoDeElementos(Object elemento) {
	   
	   if(elemento instanceof IfStatement) {
			 partesDelMetodo.add("iFSTATEMENt");
			 IfStatement ifstate = (IfStatement) elemento;
			 partesDelMetodo.add(ifstate.getExpression().toString());
			 if (ifstate.getThenStatement() != null) {
				 partesDelMetodo.add("iFTHEn");
				 Statement rec = ifstate.getThenStatement();
				 Block recursiva = (Block) rec;
				 int num = 0;
				 while(recursiva.statements().size() > num) {
					 Object ola = recursiva.statements().get(num);
					 parseoDeElementos(ola);
					 num ++;
				 }
				}
			 if(ifstate.getElseStatement() != null) {
				 partesDelMetodo.add("iFELSe");
				 Statement rec = ifstate.getElseStatement();
				 Block recursiva = (Block) rec;
				 int num = 0;
				 while(recursiva.statements().size() > num) {
					 Object ola = recursiva.statements().get(num);
					 parseoDeElementos(ola);
					 num ++;
				 }
			 }
		 }else if(elemento instanceof WhileStatement) {
			 partesDelMetodo.add("wHILESTATEMENt");
			 WhileStatement Wstate = (WhileStatement) elemento;
			 partesDelMetodo.add(Wstate.getExpression().toString());
			 if (Wstate.getBody()!= null) {
				 partesDelMetodo.add("wHILEBODy");
				 Block recursiva = (Block) Wstate.getBody();
				 int num = 0;
				 while(recursiva.statements().size() > num) {
					 Object ola = recursiva.statements().get(num);
					 parseoDeElementos(ola);
					 num ++;
				 }
				}
		 }else {
			 partesDelMetodo.add("iNCONDICIONAl");
			 partesDelMetodo.add(elemento.toString());
		 }
	   
   }
    
    /**
     * Reads a ICompilationUnit and creates the AST DOM for manipulating the
     * Java source file
     *
     * @param unit
     * @return
     */

    private static CompilationUnit parse(ICompilationUnit unit) {
        ASTParser parser = ASTParser.newParser(AST.JLS3);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setSource(unit);
        parser.setResolveBindings(true);
        return (CompilationUnit) parser.createAST(null); // parse
    }

		//////////////////////////////////////////////////////////////////////
		//////////////////////////////////////////////////////////////////////
//		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
//		MessageDialog.openInformation(
//				window.getShell(),
//				"PluginProject3",
//				"Hello, Eclipse world");
//		return null;
	}

