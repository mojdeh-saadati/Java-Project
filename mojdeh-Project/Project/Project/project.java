import java.util.*;
import java.io.*;
import java.lang.*;
class Int
{
	String name;
	int value;
	int location = 0;
		Int(String s ,int a ,int b)
		{
		value = a;
		name = s;
		location = b;
		}
		Int(String s , int a)
		{
		value = a;
		name = s;		
		}
		Int(String s)
		{
		name = s;				
		}

}
class Method
{
//badan bayad vase in ke tabam vorodi ham begire ye karai bokonam!
	boolean isThere = false;
	boolean checkDefinition = true;
	String name = "";
	String returnType = "";
	int returnValueI = 0 ;
	LinkedList <String> sorceCodeOfMethod = new LinkedList <String> ();  

	Method(String a , String b ,LinkedList <String>  sorce)
	{
	sorceCodeOfMethod = sorce ;
	name = a;
	returnType = b;	
	}


	Method(String a , String b )
	{
	name = a;
	returnType = b;		
	}

}
//--------------------------------------------------------------------
class Scaner 
{
int numberOfMethod = 10;
static int L = -1;
static Scanner scan;
static String tokens[];
static String Line = "";
static File file;
boolean cleanComments = false;
	
	Scaner()throws Exception
	{
	}
	void firstForScanner()throws Exception
	{
	Scanner sc = new Scanner(System.in);
	String fileName = sc.nextLine().trim();
	file = new File(fileName);
	scan = new Scanner(file);	
	}
	boolean first()throws Exception
	{
	Scanner scanFirst= new Scanner(file);
//	scanFirst = new Scanner(file);
	int numberOfParantez = 0;
	int numberOfAkolad = 0;
	int numberOfKroshe = 0;
	boolean minesNumber  = true;
	while( scanFirst.hasNextLine() )	
		{
		String Li = scanFirst.nextLine();
		for(int i = 0 ; i < Li.length() && minesNumber; i++)
			if(Li.charAt(i) == '{' )
				numberOfAkolad++;
			else if(Li.charAt(i) == '}' )
				{numberOfAkolad--;
				if(numberOfAkolad < 0)
					minesNumber = false;
				}
			else if(Li.charAt(i) == '(' )
				numberOfParantez++;
			else if(Li.charAt(i) == ')' )
				{numberOfParantez--;
				if(numberOfParantez < 0)
					minesNumber = false;
				}
			else if(Li.charAt(i) == '[' )
				numberOfKroshe++;
			else if(Li.charAt(i) == ']' )
				{numberOfKroshe--;		
				if(numberOfKroshe < 0)
					minesNumber = false;
				}
		}
	if(minesNumber == false)
	{
	System.out.println("one of }, ] ,) is closed but is not opened !");
		return false;
	}

	if(numberOfParantez != 0  ||numberOfAkolad != 0 ||numberOfKroshe != 0 )
		{
		if(numberOfParantez != 0)
			System.out.println("the parantezs is not match!");
		if(numberOfAkolad != 0)
			System.out.println("the Akolads is not match!");
		if(numberOfKroshe != 0)
			System.out.println("the Kroshe is not match!");
		return false;
		}
	return true;						
	}


	void tokenize( String line )throws Exception
	{
	LinkedList< String > toks = new LinkedList <String>();
	Parser pars = new Parser();
	String str = "";
	String g = "";
	System.out.println("line "+L+":"+line);
		for(int i = 0; i < line.length() ; i++ , g = "" )
		{
		if( !pars.delimeterFind(g + line.charAt(i)) && line.charAt(i) != ' ')
			str += line.charAt(i);
		else 
			{
			if(!str.equals(""))
				toks.add(str.trim());	
			str = "";			
			if(line.charAt(i) == '"')
				{String w = "";
				w += line.charAt(i++);
				toks.add( w );		
				str = "";				
				while(line.charAt(i) != '"')
					str += line.charAt(i++);				
				toks.add(str.trim());		
				str = "";		
				toks.add( str + line.charAt(i) );				
				str = "";
				}
		
			if( pars.delimeterFind(g + line.charAt(i)) && line.charAt(i) != '"')
				{
				String w = "";
				w += line.charAt(i);
				toks.add( w.trim() );					
				}
			}
		}				 
	if(str.length() != 0 )
		toks.add(str.trim() );

	LinkedList <String> deleteTok = new LinkedList <String>();

	for(int j = 0 ; j < toks.size() ; j++ )
		{
		if(  !toks.get(j).trim().equals("") )
		deleteTok.add(toks.get(j));
		}

	tokens = new String[ deleteTok.size() ];
	
	for(int j = 0 ; j < deleteTok.size() ; j++ )
		{tokens[j] = deleteTok.get(j);
		}
		
	}
	static String cleanComment(String line)throws Exception 
	{
	boolean cleanComments = false;
	String str = "";
	for(int i = 0 ; i < line.length()-1 ; i++)
		{
	if(!(line.charAt(i)=='/'&&line.charAt(i+1)=='/')&&!(line.charAt(i)=='/'&& line.charAt(i+1) =='*')&&!(line.charAt(i)=='*'&& line.charAt(i+1)=='/')&&!cleanComments)
			str += line.charAt(i);
		else if(line.charAt(i) == '/' &&line.charAt(i+1) == '/')
			return str;	
		else if(line.charAt(i) == '/' &&line.charAt(i+1) == '*')
			{cleanComments =true;  return str;}	
		else  if(line.charAt(i) == '*' &&line.charAt(i+1) == '/')
			cleanComments = false;	
		}
	if(line.length() != 0)	
		if( line.charAt(line.length()-1) != '/')
			str += line.charAt( line.length()-1 ); 	
	return str;	
	}
	
	
	int priorityOperand(char ch)
	{
//	if(ch == '(')
//		return 0;
	if(ch == '=')
		return 0;
	if(ch == '+' || ch == '-')
		return 1;
	if(ch == '*' || ch == '/' || ch == '%')
		return 2;
	return -1;
	}	

}

class Parser extends Scaner
{
	LinkedList <String> lineWhile;
	
	String[] tokensRole                 ;// = new String[tokens.length];
	LinkedList<String> reserveWord       = new LinkedList<String>(); 
	LinkedList<String> delimeter         = new LinkedList<String>(); 
	LinkedList<Int>identifierInt         = new LinkedList<Int>(); 
	LinkedList<Method> identifierMethod  = new LinkedList<Method>(); 
	Parser()throws Exception
	{
	reserveWord.add("while");
	reserveWord.add("if");
	reserveWord.add("int");
	reserveWord.add("double");
	reserveWord.add("new");
	reserveWord.add("class");
	reserveWord.add("public");
	reserveWord.add("static");
	reserveWord.add("return");
	reserveWord.add("void");
	reserveWord.add("println");
	reserveWord.add("print");
	reserveWord.add("read");
	reserveWord.add("System");
	reserveWord.add("out");
	reserveWord.add("in");

	delimeter.add(")");
	delimeter.add("(");
	delimeter.add("*");
	delimeter.add("&");
	delimeter.add("+");
	delimeter.add("-");
	delimeter.add("_");
	delimeter.add("?");
	delimeter.add("!");
	delimeter.add("=");
	delimeter.add("%");
	delimeter.add("\"");
	delimeter.add("'");
	delimeter.add(",");
	delimeter.add(">");
	delimeter.add("<");
	delimeter.add("}");
	delimeter.add("{");
	delimeter.add("]");
	delimeter.add("[");
	delimeter.add(".");
	delimeter.add(";");
	delimeter.add("/");

	}
	boolean reserveWordFind(String str)throws Exception
	{
	for(int i = 0 ; i < reserveWord.size() ; i++)
		if(reserveWord.get(i).equals(str))
			return true;
		return false;
	
	}

	boolean delimeterFind(String str)
	{
	for(int i = 0 ; i < delimeter.size() ; i++)
		if(delimeter.get(i).equals(str))
			return true;
		return false;
	
	}
	int identifierFind(String str)
	{
	for(int i = 0 ; i < identifierInt.size() ; i++)
			if(identifierInt.get(i).name.equals(str))
			return i;
	for(int i = 0 ; i < identifierMethod.size() ; i++)
		if(identifierMethod.get(i).equals(str))
			return i;
	return -1;	
	
	}

	void giveRole() throws Exception
	{
	tokensRole = new String[tokens.length];
	for(int i = 0 ; i < tokens.length ; i++)
		{
		boolean ok = true;
	
		if( tokens[i].charAt(0)-'0' >= 0 && tokens[i].charAt(0)-'0' <= 9)
			{
			for(int j = 0 ; j < tokens[i].length() && ok; j++)
				if(tokens[i].charAt(j)-'0' < 0 && tokens[i].charAt(j)-'0' > 9)
						ok = false;
			}
		else ok = false;	
		if(!ok && tokens[i].charAt(0)-'0' >= 0 && tokens[i].charAt(0)-'0' <= 9)
			System.out.println("there is no symbol "+ tokens[i] +" in Line "+L);	
		if(ok)
			tokensRole[i] = "n";
		
		if(reserveWordFind(tokens[i]))
			tokensRole[i] = "r";
		else if(delimeterFind(tokens[i]))
			tokensRole[i] = "d";
		else if(!ok)
			tokensRole[i] = "x";

		for(int w = 0 ; w < identifierInt.size() ; w++)
			if(identifierInt.get(w).name.equals(tokens[i]))
				tokensRole[i] = "I";
		
		for(int w = 0 ; w < identifierMethod.size() ; w++)
			if(identifierMethod.get(w).name.equals(tokens[i]))
				tokensRole[i] = "M";
		}
		
		
	}
	boolean grammerFirst(int location)throws Exception
	{
	boolean dontRun = true;
	String statement = "";
	for(int i = 0 ; i < tokens.length ; i ++)
		statement += tokensRole[i];

	if(statement.length() >= 4 && statement.charAt(0) == 'r' && statement.charAt(1)=='r' &&  statement.charAt(2)=='r' && statement.charAt(3)=='x' && 		statement.charAt(4)=='d')
		if(tokens[0].equals("public") && tokens[1].equals("static") )
			{
			if(!compileDefinitionMethod()) 
				dontRun = false;				 
			}
				//.....
	if(statement.length() >= 3 && statement.charAt(0) == 'r' && statement.charAt(1)== 'x' &&  statement.charAt(2)== 'd')
		if(tokens[0].equals("int") )
			{
			
			if( !compileAndRunDefinitionInt() )//badan in ja hozeye shenakhto minevisam!
				dontRun = false;	
			}
	return dontRun;
	
	
	}
	
	boolean grammer()throws Exception	//bayad ino ke ham in zirmajmoe ye on bashe ham ono dorost konam 
	{
	boolean dontRun = true;
	String statement = "";
	boolean oneWorkIsDone = false;
				
	for(int i = 0 ; i < tokens.length ; i ++)
		{
		statement += tokensRole[i];
		}
	if(statement.charAt(0) == 'r' && statement.charAt(1)== 'x' &&  statement.charAt(2)== 'd')
		if(tokens[0].equals("int") )
			if( compileAndRunDefinitionInt() )
				{
				dontRun = false;	
				oneWorkIsDone = true;
				}
				//......
				
	if(tokens.length >= 7 && statement.charAt(0) == 'I' && statement.charAt(1) == 'd' && statement.charAt(2) == 'r' && statement.charAt(3)=='d' && statement.charAt(4)=='r' && statement.charAt(5)=='d'&& statement.charAt(6)=='r')
		if( tokens[6].equals("read")  )
			if(compileRead())
				{
				runRead();
				oneWorkIsDone = true;
				}
	if(tokens.length >= 5 && statement.charAt(0) == 'r' && statement.charAt(1) == 'd' && statement.charAt(2) == 'r' && statement.charAt(3)=='d' && statement.charAt(4)=='r')
	 if( tokens[4].equals("print") ||  tokens[4].equals("println")  )
			{
			if(compilePrint())
				runPrint();
				oneWorkIsDone = true;
			}
			
				//......	
	/*if(statement.charAt(0) == 'r' && (statement.charAt(1)== 'i' || statement.charAt(1)== 'n' ) && statement.charAt(2)=='d')
		if( tokens[0].equals("return") && tokens[2].equals(";") )
			{
			runReturn( identifierMethod.get( methodNumber ));
			oneWorkIsDone = true;
			}
				//......
	
	if( tokens.length >= 4 && statement.charAt(0) == 'r' && statement.charAt(1) == 'd' && statement.charAt( statement.length()-1 ) == 'd')
		{
		if( tokens[0].equals("if") )
			if( compileIf() != -1)
				return runIf( compileIf() );
	
		}
	*/
	for(int i = 1 ; i < statement.length()-1 ; i++)	
		if(statement.charAt(i) == 'M' )
			if(statement.charAt(i-1) != 'r')
				{
				runMethod( identifierMethod.get( identifierFind(tokens[i]) ));
				oneWorkIsDone = true;	
				}
				//......
	if(tokens[0].equals( "while" ) && tokens[1].equals( "(" ) )
		if( !compileWhile() )
			{dontRun = false;
			oneWorkIsDone = true;
			}
		else 
			runWhile(lineWhile);
				//......
	boolean haveReserveWord = false;
	for(int i = 0 ; i < tokens.length ; i++)	
		if(tokensRole[i].equals("r") )
			{
			haveReserveWord = true;
			break;
			}
				//......
	if(!haveReserveWord)		
		if(!tokens[tokens.length-1].equals("}") && compileStatement(tokens, tokensRole) )
			{
			runStatement();	
			oneWorkIsDone = true;
			}
				//......
	if( tokens[tokens.length-1].equals("}") )	
		oneWorkIsDone = true;	
	if(oneWorkIsDone == false)
		System.out.println("the Line:"+L+" is not cerrect!");
	
	return oneWorkIsDone;
	
	}		
	
	boolean compileRead()
	{
	boolean error = true;
	if( !tokens[tokens.length-1].equals(";") )
		{
		System.out.println("the Line:"+ L +" should have simicolon at the end.");
		error = false;
		}		
	else
		{
		//badane bayad yadam bashe ke vase amalgaram kelas benevisam in jori nemshe ke?
		boolean haveLocationForPrint = false;	
		int numberOfParantez = 0;
		for(int i = 0 ; i < tokens.length ; i++)
			if(tokens[i].equals("(") )
				numberOfParantez++; 	
			else if(tokens[i].equals(")") )
				numberOfParantez--; 
			else if( tokens[i].equals("=") )	
				haveLocationForPrint = true;			
		
		if(numberOfParantez != 0)		
			{
			System.out.println("the number of parantez is not match in Line:" + L);
			error = false;
			}		
		if(haveLocationForPrint == false)		
			{
			System.out.println("there isn't any location for get return of method read in Line:" + L);
			error = false;
			}		

		}
	return error;
	//bayad system,in ro ham benevisam
	}
	
	void runRead()throws Exception
	{
	Scanner s = new Scanner(System.in);
	boolean hasEqualsDelimeter = false;
	int a = s.nextInt();
	int w = 0;
	for(int i = 0 ; i < tokens.length ; i++)
		if(tokens[i].equals("="))
			{
			hasEqualsDelimeter = true;
		      	w = identifierFind(tokens[i-1]);	
			break;
			}
	identifierInt.get(w).value = a;
	
	}
	
	int runReturn(Method mt) throws Exception
	{
	int p = 0;
	for(int i = 0 ; i < tokens.length ; i++)
		if(tokens[i].equals("return"))
			p = i;
	if(!tokensRole[p+1].equals("n"))	
		{
		int s = identifierFind(tokens[p+1]);	
			if(s != -1)	
				if(mt.returnType.equals("int")) 
					return identifierInt.get(s).value;				
		}
	else 
		return Integer.parseInt(tokens[p+1]);
	return 0;
	}
	
	boolean compileWhile()throws Exception
	{
	boolean error = true;
	if(!tokens[1].equals("(") || !tokens[tokens.length-1].equals(")") )
		{
		System.out.println("the while in Line:"+ L +" should have parantez.");
		error = false;
		}		
	int numberOfParantez = 0;
	
	lineWhile = new LinkedList <String>(); 

	lineWhile.add( cleanComment(Line));
	do
	{
	String lin = scan.nextLine();  
	lineWhile.add( cleanComment(lin) );
	tokenize(cleanComment(lin));
	for(int i = 0 ; i < tokens.length ; i ++)
		if(tokens[i].equals("{"))		
			numberOfParantez++;
		else if(tokens[i].equals("}") )		
			numberOfParantez--;					
	}while(numberOfParantez != 0);
	
	
	return error;	
	}
	boolean runWhile(LinkedList <String> bodyWhile)throws Exception
	{
	boolean ok = true;
	while( runIf(compileIf() ) )
		for(int i = 0 ; i < bodyWhile.size() && ok ; i++)
			{
			tokenize( bodyWhile.get(i) );
			ok = grammer();
			}
	
	return ok;
	}
	int compileIf()
	{
	int typeOperand;
	typeOperand = -1;
	boolean ok = false; 
	
	for(int i = 0 ; i < tokens.length-1 ; i++)
	{
		if(tokensRole[i].equals("x") )
			return -1;
		if(tokens[tokens.length-1].equals(";"))
			return -1;			
		if(tokens[i].equals("!")  && !ok)	
			{
				typeOperand = 1;
				ok = true;
			}	
		else if (tokens[i].equals("!") && tokens[i+1].equals("=")  && !ok )
			{
				typeOperand = 2;
				ok = true;
			}	
		else if (tokens[i].equals("=") && tokens[i+1].equals("=") && !ok)
			{
				typeOperand = 3;
				ok = true;
			}	
		else if (tokens[i].equals(">") && tokens[i+1].equals("=") && !ok)
			{typeOperand = 4;ok = false;}	
		else if (tokens[i].equals("<") && tokens[i+1].equals("=")  && !ok)
			{typeOperand = 5;ok = false;}	
		else if (tokens[i].equals(">") && !tokens[i+1].equals("=")  && !ok)
			{typeOperand = 6;ok = false;}	
		else if (tokens[i].equals("<") && !tokens[i+1].equals("=")  && !ok)
			{typeOperand = 7;ok = false;}	
		else if(typeOperand != -1 && !ok)
			{
			typeOperand = -1;
			System.out.println("the statement in Line:"+ L +" is not correct.");		
			break;	
			}
	}
			
	return typeOperand;	
	}	

	boolean runIf(int a)throws Exception
	{

	int val[] = new int[2];
	for(int i = 1 ; i < tokens.length ; i ++)	
		{
		if( tokens[i].equals("=") || tokens[i].equals("<") || tokens[i].equals(">") || tokens[i].equals("!") )
			{
			if(a >= 6 )
				{
				if( tokensRole[i-1].equals("I") )
					val[0] = identifierInt.get( identifierFind(tokens[i-1]) ).value;	
					
				else if( tokensRole[i-1].equals("n") )
					val[0] = Integer.parseInt(tokens[i-1]);	
				 if( tokensRole[i+1].equals("I") )
					val[1] = identifierInt.get( identifierFind(tokens[i+1]) ).value;	
				else if( tokensRole[i+1].equals("n") )
					val[1] = Integer.parseInt(tokens[i+1]);	

				}
			if(a < 6 )
				{
					
				if( tokensRole[i-1].equals("I") )
					{
					val[0] = identifierInt.get( identifierFind(tokens[i-1]) ).value;	
					}
				else if( tokensRole[i-1].equals("n") )
					val[0] = Integer.parseInt(tokens[i-1]);	
				 if( tokensRole[i+2].equals("I") )
					{
					
					val[1] = identifierInt.get( identifierFind(tokens[i+2]) ).value;
					}	
				else if( tokensRole[i+2].equals("n") )
					val[1] = Integer.parseInt(tokens[i+2]);					
				}			
			break;
			}
		}	
	if(a == 2)
		if(val[0] != val[1])
			return true;
		else 
			return false;	
	if(a == 3)
		{
		if(val[0] == val[1])
			return true;
		else 
			return false;	
		}
	if(a == 4)
		if(val[0] >= val[1])
			return true;
		else 
			return false;	
	if(a == 5)
		if(val[0] <= val[1])
			return true;
		else 
			return false;	
	if(a == 6)
		if(val[0] > val[1])
			return true;
		else 
			return false;	
	if(a == 7)
		if(val[0] < val[1])
			return true;
		else 
			return false;	
	
	return true;
	
	}	
	boolean compileStatement(String tokens[] , String tokensRole[]) throws Exception
	{
	boolean error = true;
	if(tokens.length >=1 &&  ! tokens[tokens.length -1 ].equals(";") )
		{
		System.out.println("the statement in Line:"+ L +" should have simicolon.");
		error = false;
		}		
	int j = 0;
	for(int i = 0 ; i < tokens.length-1 ; i ++)	
		if(tokens[i].equals("(") )
			j++;
		else if(tokens[i].equals(")") )
			{
			j--;
			if(j < 0)
				{
				System.out.println("the parantez is closed but is not opened:"+ L +" .");
				error = false;
				}		
				
			}
		else if(tokens.length >= 1 && tokensRole[i].equals("d") && tokensRole[i+1].equals("d") )
			if( !tokens[i].equals("(") && !tokens[i+1].equals("(") &&!tokens[i].equals(")") && !tokens[i+1].equals(")") )
			{
			System.out.println("there should be some word between Operand intLine:"+ L +" .");
			error = false;
			}		
	
	if( j != 0 )
		{
		System.out.println("the number of parantez is not match in Line:"+ L +" .");
		error = false;
		}		
	return error;
	}
	
	void runStatement( )
	{
	System.out.println("varede runStatement shod");
	Stack <String> first =  new Stack <String> ();
	Stack <Integer> second = new Stack <Integer> ();
	LinkedList <String> str = new LinkedList <String> ();
	int numberOfParantez = 0;
	int c = 0 ;
	for(int j = 2 ; j < tokens.length-1 ; j++)
		if( tokensRole[j].equals("d") )			
		{	
			if(tokens.equals(")") )	
				{
				while(!first.get(0).equals("(") )		
					{
					System.out.println("eeeeeeeeeeeeee"+first.get(0));
					str.add(first.pop());
					}
				
				first.remove(0);
				continue;
				}

				int i = 0;	
				while( !tokens[j].equals("(")  && first.size() >= 1 && !first.get(0).equals("(") &&
				priorityOperand( first.get(0).charAt(0) ) >= priorityOperand( tokens[j].charAt(0) ) )
					{
					System.out.println("poooooooooooooooooopo"+first.get(0));
					str.add(first.pop());
	
					break;
					}
			
				
			first.push(tokens[j]);

		}
		else 
			{System.out.println("eeeeeeeeeeeeee"+tokens[j]);
			str.add(tokens[j]);}	

	while(first.size() != 0)			
		str.add ( first.pop() );

	for(int i = 0 ; i < str.size() ; i++ )
		System.out.print("  linkedList str:"+str.get(i) );

	int a = 0 ; int b = 0 ;
	for(int j = 0 ; j < str.size() ; j++)
		{
		
		//for(int i = 0 ; i < second.size() ; i++ )
		//	System.out.print("  stack second:"+second.get(i) );

		if(delimeterFind(str.get(j)) == false)
			{
			if(str.get(j).charAt(0) >= '0' && str.get(j).charAt(0) <= '9')	
				{System.out.println("varede ghesmate number shod");
				second.push( Integer.parseInt (str.get(j)) ); }
			else 
				{System.out.println("varede ghesmate moteghayer shod");
				second.push(identifierInt.get(identifierFind(str.get(j))).value );
				}
			}	
		if(second.size() >= 2)
			{
			a = second.pop();
			b = second.pop();
			System.out.println("operation between a and b is:" + str.get(j) +"  a and b"+a+"  "+b );
			second.push(operation( a,b , str.get(j)));
			
			}
	
		for(int i = 0 ; i < second.size() ; i++ )
			System.out.print(" element of second:" + second.get(i) );
		System.out.println();		
		}
	identifierInt.get(identifierFind(str.get(0))).value = second.pop() ;
	//System.out.println("ssssss"+identifierInt.get(identifierFind(str.get(0))).value );
	

	}
	
	
	boolean compileAndRunDefinitionInt()
	{	
	
	boolean error = true;

	for(int i = 1 ; i < tokens.length-1 ; i++)	
		if(tokensRole[i].equals("x") )
			if(tokens[i+1].equals(","))	
				{
				identifierInt.add( new Int(tokens[i]) );
				i++;	
				}
			else if(tokens[i+1].equals("=") && tokensRole[i+2].equals("n") && tokensRole[i+3].equals("d") )	
				{
				identifierInt.add( new Int(tokens[i] , Integer.parseInt(tokens[i+2])) );	
				i+= 3;
				}	
			else 
				{
				System.out.println("the definition of Integer in Line:"+ L +" is not correct.");
				error = false;
				}		
	return error;
	}
	
	boolean compileAndRead()throws Exception
	{
	boolean error = true;
	int i;
	for( i = 0 ; i < tokens.length - 2 ; i++ )
		if(tokens[i].equals("read") )
			if(!(tokens[i+1].equals("(") || tokens[tokens.length-2].equals("(") || tokens[tokens.length-2].equals(";") ))	
			{
			System.out.println("the definition of method read  in Line:"+ L +" is not correct.");
			error = false;
			break;
			}		
			else 
			{
			Scanner s = new Scanner(System.in);
			for(int j = 0 ; i < identifierInt.size() ; i++)
				if(identifierInt.get(j).name.equals(tokens[i+2]))
						identifierInt.get(j).value = s.nextInt();
			break;		
			}	
			
	return error;
	}	
	
	boolean compilePrint()throws Exception
	{
	boolean openBracket = false;
	boolean error = true;

	for(int i = 1 ; i < tokens.length - 2 ; i++ )
		if(tokensRole[i].equals("x") && openBracket == false && !tokens[i-1].equals("\"") )
		{
		System.out.println("the word "+ tokens[i] +" in Line:"+ L +" is not defineded.");
		error = false;
		}		
		else if(tokensRole[i].equals("d") && tokensRole[i+1].equals("d") && !tokens[i].equals("\"") && !tokens[i+1].equals("\""))	
		{
		System.out.println("there is no simbol between the delimeter in Line:"+L);
		error = false;
		}		
	if( !tokens[5].equals("(") || !tokens[tokens.length-2].equals(")")  )	
		{
		System.out.println("for use this method print should use parantez :");
		error = false;
		}		
				
	if( !tokens[tokens.length-1].equals(";") )	
		{
		System.out.println("the statement in Line:"+ L +" is not close.");
		error = false;
		}	
	
	return error;
	}		
	void runPrint()throws Exception
	{
	
	int c = 0;
		for(int i = 5 ; i < tokens.length -2 ; i++ , c = 0 )
			{
			
			if(tokens[i].equals("\""))
				while( !tokens[i].equals("\"") )
					System.out.print( tokens[i].charAt(c++) );
			else if( tokensRole[i].equals("I") )
				System.out.print(identifierInt.get( identifierFind(tokens[i]) ).value);
						
			}
						
	if(tokens[4].equals("println"))
		System.out.println();	
	}

	boolean compileDefinitionMethod()throws Exception
	{
	boolean error = true;
	if(tokens[tokens.length - 1].equals(";") )
		{
		System.out.println("the definition in Line:"+ L +"sholdn't have simicolon.");
		error = false;
		}		
		
	if( !tokensRole[2].equals("r") )
		{
		System.out.println("the definition in Line:"+ L +" doesn't have return type correct.");
		error = false;
		}		
		
	else 	{		
		int numberOfAkolad = 0;
		boolean doReturn = false;
		String returnMethod = tokens[2];
		String methodName = tokens[3];
		L++;
		String str = scan.nextLine();
		System.out.println("str ghabl az clean comment :"+str);
		str = cleanComment(str);
		tokenize(str);
	
		if(! tokens[0].equals("{") )
			{
			System.out.println("the method in Line:"+ L +" doesn't have body.");
			error = false;
			}
	
		else 
		{
		LinkedList<String> lines = new LinkedList <String> ();	
			numberOfAkolad ++;

			do
			{
			
			L++;
			str = scan.nextLine();  
			lines.add(cleanComment(str) );
			tokenize(cleanComment(str));
			for(int i = 0 ; i < tokens.length ; i ++)
				{
				if(tokens[i].equals("{"))		
					numberOfAkolad++;
				else if(tokens[i].equals("}") )		
					numberOfAkolad--;					
				else if( tokens.length >= 2 && tokens[i].equals("return") && tokensRole[i+1].equals("I") && (!returnMethod.equals("int")) )
				{
					System.out.println("this method should return "+ returnMethod + "and return Type is not correct in Line:" + L);
					error = false;
				}
				}				
			
			}while(numberOfAkolad != 0);
		//System.out.println("return Method :"+ returnMethod +" methodName :"+methodName );	
		identifierMethod.add( new Method(methodName,returnMethod , lines ) );			
		
		}
	}
	//System.out.println("az method kharej mishavad");
	return error;	
	}
	
	
	
	boolean runMethod( Method mt)throws Exception
	{
	boolean ok = true;
	LinkedList <String> Body = mt.sorceCodeOfMethod;
	for(int i = 0 ; i < Body.size() && ok; i++)
		{
		String str = cleanComment(Body.get(i));
		tokenize(str);
		giveRole();
		int location = 0 ;
		for(int j = 0 ; j < tokens.length ; j++)
			if( tokens[j].equals("{") )		
				location++;	
			else if( tokens[j].equals("}") )		
				location--;	
//		grammer(location);

		if(tokens.length >=1 && !tokens[0].equals("if") && !tokens[0].equals("while"))
			ok = grammer();
		else if(tokens.length >=1 && tokens[0].equals("if") )	
			if(compileIf() != -1)	
				if( !runIf( compileIf() ) )
					i++;		
		}
	
	return ok;	
	}	



	boolean compileDefintionInt()throws Exception
	{
	boolean error = true;
	if(!tokens[tokens.length - 1].equals(";") )		
		{
		System.out.println("the Statement in Line:"+L+"is not finished.");
		error = false;
		}
	for(int i = 1 ; i < tokens.length-1 ; i++ )
		{
		if(tokensRole[i] == "x" && tokensRole[i+1] == "x")
			{
			System.out.println("there is no delimeter between the word in Line:"+ L);
			error = false;
			}
		if(tokensRole[i] == "d" && tokensRole[i+1] == "d")
			{
			System.out.println("there is no simbol between the delimeter in Line:"+L);
			error = false;
			}

/*		if(tokensRole[i] == "n")		
			for(int u = 0 ; u < tokens[i].length() ; u++)
				if(tokens[i].charAt(u) == '.')
				{
				System.out.println("you don't have permision to equals integer with double in Line:"+L);
				error = false;
				}
*/
		}
	return error;
	}

	boolean runDefinitionInt(int i)throws Exception
	{
	if(tokens[i].equals(";"))
		return true;
	else if(tokensRole[i].equals("x") && tokens[i+1].equals("=") && tokensRole[i+2].equals("n"))
		{
		identifierInt.add(new Int(tokens[i] , Integer.parseInt(tokens[i+2] ) ) );	
		return runDefinitionInt(i+3);	
		}
	else if(tokensRole[i].equals("x") && tokens[i+1].equals(","))
		{
		identifierInt.add(new Int(tokens[i]) );	
		return runDefinitionInt(i+2);
		}
	return true;
	}
				

		
	boolean classDefinitionCompile()throws Exception
	{
	boolean error = true;
	if(!tokensRole[1].equals("x"))
		{
		System.out.println("your class doesn't have name in Line"+L);
		error = false;
		}
	return error;
	
	}

/*
	boolean ifCompile( )throws Exception
	{
	//yadam bashe simicolone akhare jomlaro ham check konam!
	int parantez = 0;
	boolean error = true;
	boolean hasOperand = false;
	for(int i = 0 ; i < tokens.length ; i++)
		if(tokens[i] == "(")
			parantez++;
		else if(tokens[i] == ")")
			parantez--;	
		else if(tokensRole[i] == "x")
			{
			System.out.println("there is no symbol "+tokens[i]+" in Line "+L);
			error = false;
			}
		else if(tokensRole[i] == "d")
			hasOperand = true;			

	if(!hasOperand)
			{
			System.out.println("there is no sharti stamement in Line "+L);
			error = false;
			}

	if(parantez != 0)
			{
			System.out.println("the parantez is not match in Line "+L);
			error = false;
			}
	return error;
	}
*/

	int operation(int a , int b ,String s)
	{
	if(s.equals("+") )
		return a + b;	
	if(s.equals("-") )
		return a - b;	
	if(s.equals("*") )
		return a * b;	
	if(s.equals("/") )
		return a / b;	
	if(s.equals("%") )
		return a % b;	
	if(s.equals("=") )
		return a;			
	return a;
	}


}
class RunProject extends Parser
{
	RunProject() throws Exception
	{
	}
	int hasMainMethod()throws Exception
	{
		do{
		L++;
		String str = scan.nextLine();
		str = cleanComment(str);

		if(str.equals(""))
			continue;

		tokenize(str);
		giveRole();
		grammerFirst(0);
		System.out.println();				
		System.out.println();				
		}while( scan.hasNextLine() );
	
	for(int i = 0 ; i < identifierMethod.size() ; i++ )
		System.out.println(identifierMethod.get(i).name );

	for(int i = 0 ; i < identifierMethod.size() ; i++)
		if( identifierMethod.get(i).name.equals("main") )	
			{
			return i;
			}
	return -1;		
	}


	void run()throws Exception
	{
	if( first() )
		{
		boolean ok = false;
		int mainNumber = hasMainMethod();
		if(mainNumber != -1)
			if( !runMethod(identifierMethod.get(mainNumber)) );	
				ok = false;
		}

	}
}



class Main
{
	public static void main(String[] args)throws Exception
	{
		Scaner sc = new Scaner();
		sc.firstForScanner();
		RunProject runP = new RunProject();						
		runP.run();
					
	}

} 

