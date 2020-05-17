import java.math.BigDecimal;
import java.util.Stack;

public class Calculate {
	
	private final static int scale = 9;   //��������ѭ��С��ʱ������λ��
	
	private static Stack<BigDecimal> numberStack = new Stack<BigDecimal>();   // ����ջ
	private static Stack<Character> symbolStack = new Stack<Character>();   //����ջ
	
	@SuppressWarnings({ "finally", "deprecation" })
	public static BigDecimal calculate(String numStr) {
		try {
			StringBuffer temp = new StringBuffer();   //�����λ������
			for(int i = 0 ; i < numStr.length(); i++) {
				char ch = numStr.charAt(i);   //��ȡ��iλ�õ��ַ�
				if(isNumber(ch)) {
					temp.append(ch);   //���뵽���ֻ�����
				}
				else {
					String tempStr = temp.toString();   //������ת��Ϊ�ַ���
					if(!tempStr.isEmpty()) {
						BigDecimal num = new BigDecimal(tempStr);
						
						System.out.println(num);   //===============================================
						
						numberStack.push(num);   //���ֽ�ս
						temp = new StringBuffer();   //�������ֻ���
					}
					//�ж�����������ȼ�������ǰ���ȼ�����ջ�������ȼ������ȰѼ���ǰ��������
					while(!comparePri(ch) && !symbolStack.empty()) {
						BigDecimal b = numberStack.pop();   //��ջ��ȡ�����֣�����ȳ�
						BigDecimal a = numberStack.pop();
						//ȡ�������������Ӧ���㣬���ѽ����ջ������һ������
						switch(symbolStack.pop()) {
						case '+': numberStack.push(a.add(b)); break;
						case '-': numberStack.push(a.subtract(b)); break;
						case '*': numberStack.push(a.multiply(b)); break;
						case '/': //������������С���ǻ��׳��쳣��Ҫ���þ���
							try {
								numberStack.push(a.divide(b));
							} catch (ArithmeticException e) {
								numberStack.push(a.divide(b, scale, BigDecimal.ROUND_HALF_UP));   //����ѭ��С��ʱ�����
								System.out.println(scale+"========================");
							}break;
						default: break;
						}
					}
					symbolStack.push(ch);   //������ջ�����Ϊ)��Ҫ��ջ��������
					
					System.out.println(symbolStack.peek());//=====================================
					
					if(ch == ')') {   //ȥ����
						symbolStack.pop();
						symbolStack.pop();
					}
				}
			}
			
			System.out.println(numberStack.peek());//=============================================
		} catch(Exception e) {  //��ʽ��������޷���������
			GUI.error();
		} finally {
			return numberStack.pop();   //������������ջ�����ּ�Ϊ������
		}
	}
	
	private static boolean isNumber(char c) {   //�ж��ַ��Ƿ�Ϊ����
		if((c>='0' && c<='9') || c == '.')
			return true;
		return false;
	}
	
	private static boolean comparePri(char symbol) {   //�ж�����������ȼ�
		if(symbolStack.isEmpty()) {
			return true;
		}
		
		char top = symbolStack.peek();   //�鿴��ջ�����Ķ��󣬵�������ջ
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
