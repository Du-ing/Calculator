import java.math.BigDecimal;
import java.util.Stack;

public class Calculate {
	
	private final static int scale = 9;   //出现无限循环小数时保留的位数
	
	private static Stack<BigDecimal> numberStack = new Stack<BigDecimal>();   // 数字栈
	private static Stack<Character> symbolStack = new Stack<Character>();   //符号栈
	
	@SuppressWarnings({ "finally", "deprecation" })
	public static BigDecimal calculate(String numStr) {
		try {
			StringBuffer temp = new StringBuffer();   //缓存多位数数字
			for(int i = 0 ; i < numStr.length(); i++) {
				char ch = numStr.charAt(i);   //获取第i位置的字符
				if(isNumber(ch)) {
					temp.append(ch);   //加入到数字缓存中
				}
				else {
					String tempStr = temp.toString();   //将数字转存为字符串
					if(!tempStr.isEmpty()) {
						BigDecimal num = new BigDecimal(tempStr);
						
						System.out.println(num);   //===============================================
						
						numberStack.push(num);   //数字进战
						temp = new StringBuffer();   //重置数字缓存
					}
					//判断运算符的优先级，若当前优先级低于栈顶的优先级，则先把计算前面计算出来
					while(!comparePri(ch) && !symbolStack.empty()) {
						BigDecimal b = numberStack.pop();   //出栈，取出数字，后进先出
						BigDecimal a = numberStack.pop();
						//取出运算符进行相应运算，并把结果进栈进项下一次运算
						switch(symbolStack.pop()) {
						case '+': numberStack.push(a.add(b)); break;
						case '-': numberStack.push(a.subtract(b)); break;
						case '*': numberStack.push(a.multiply(b)); break;
						case '/': //除法出现无限小数是会抛出异常，要设置精度
							try {
								numberStack.push(a.divide(b));
							} catch (ArithmeticException e) {
								numberStack.push(a.divide(b, scale, BigDecimal.ROUND_HALF_UP));   //出现循环小数时的情况
								System.out.println(scale+"========================");
							}break;
						default: break;
						}
					}
					symbolStack.push(ch);   //符号入栈，如果为)则要出栈两个符号
					
					System.out.println(symbolStack.peek());//=====================================
					
					if(ch == ')') {   //去括号
						symbolStack.pop();
						symbolStack.pop();
					}
				}
			}
			
			System.out.println(numberStack.peek());//=============================================
		} catch(Exception e) {  //算式输入错误，无法正常计算
			GUI.error();
		} finally {
			return numberStack.pop();   //返回最终数字栈的数字即为计算结果
		}
	}
	
	private static boolean isNumber(char c) {   //判断字符是否为数字
		if((c>='0' && c<='9') || c == '.')
			return true;
		return false;
	}
	
	private static boolean comparePri(char symbol) {   //判断运算符的优先级
		if(symbolStack.isEmpty()) {
			return true;
		}
		
		char top = symbolStack.peek();   //查看堆栈顶部的对象，但并不出栈
		if(top == '(') {
			return true;
		}
		
		switch(symbol) {
		case '(': return true;
		case '*': {
			if(top == '+' || top == '-')
				return true;
			
			else
				return false;
		}
		case '/': {
			if(top == '+' || top == '-')
				return true;
			
			else
				return false;
		}
		case '+': return false;
		case '-': return false;
		case ')': return false;
		default: break;
		}
		return true;
	}	
}
