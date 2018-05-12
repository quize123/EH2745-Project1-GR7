package Project;

import java.util.*;

import Project.CMPLX;
import Project.ReadEQfile;

/*
  This class "CalculateYbus" checks cretin components in order to calculate the Y bus matrix. For instance:
  the breaker , the connectivity of the nodes. Also, it calculates the line admittance and shunt 
  admittance of a given "cim:ACLineSegment" and it calculates the line and shunt admittance of each winding
  for a given transformer. The final form of the buses for a given system is as terminals group based on the
   of the breaker for a connectivity node.
 */



public class CalculateYBus {
	
	public CalculateYBus()	{}
	
	
	//The Method checks if the swichs for a given breaker are open or closed. Althoug it is not used for this specfic file
	//The closed breakers are removed from the ArrayList, The received ArrayList is an array of the breakers  from the EQ 
	//file and the EQ and the SSH. Then it returns an ArrayList for all closed breakers

	public ArrayList<String> BreakerStatus(ArrayList<String> status){
		for(int i = 0;i<status.size();i++){
				if(status.get(i).equals("true")){ // closed breaker
					status.remove(i);
					status.remove(i-1);
					i--;
				}
				if(status.get(i).equals("false")){ // open breaker
					status.remove(i);
					i--;
				}
		}
		return status;
	}
	
	
	//The Method checks if the terminals are connected or not. Although it is not used for this specific file
	//The open terminals are removed from the ArrayList, The received ArrayList is an array of the terminals from the EQ 
	//file and the EQ and the SSH. Then it returns an ArrayList for all connected terminals.
	
	public ArrayList<String> TerminalConnectionStatus(ArrayList<String> Terminal,ArrayList<String> Terminalstatus){
		
		String IDs = null;
		
		for(int i = 0;i<Terminalstatus.size();i++){
			if(Terminalstatus.get(i).equals("false")){ // terminal that are not connected
				IDs = Terminalstatus.get(i-1);	
				for(int j = 0;j<Terminal.size();j++){
					if(Terminal.get(j).equals(IDs)){ // Get rid of all terminal with IDs and values etc
						Terminal.remove(j);
						Terminal.remove(j);		
						break;
					}
				}
			}
		}
		return Terminal;
	}
	
	//The Method that changes the ID of the equipment terminal that refers to a transformer,
	//to a ID for an equipment that refers to a winding instead. So that we can refer the terminal to the winding
	public ArrayList<String> WindingTerminalReplacement(ArrayList<String> TransfWinding,ArrayList<String> Terminal,ArrayList<String> Transformer){
		
		for(int i = 0;i<TransfWinding.size();i+=2){
			String W = TransfWinding.get(i);		// ID for Winding
			String TID = TransfWinding.get(i+1);	//ID of the terminal for that winding
			for(int j = 0;j<Terminal.size();j+=2){
				if(TID.equals(Terminal.get(j))){
					String[] Equipment = Terminal.get(j+1).split(",");		
					for(int h = 0;h<Equipment.length;h++){
						for(int n = 0;n<Transformer.size();n++){
							if(Equipment[h].equals(Transformer.get(n))){
								Equipment[h] = W;		//For the equipment replaces the transformer ID with the winding ID
								break;
							}
						}
					}
				Terminal.set(j+1,String.join(",", Equipment));
					break;
				}
			}
		}
		return Terminal;
	}
	
	// The method creates an ArrayList that containers Terminals that share the same connectivity node 
	public ArrayList<ArrayList<String>> TerminalsInNode(ArrayList<String> Terminal,ArrayList<String> Node){
		ArrayList<ArrayList<String>> List = new ArrayList<ArrayList<String>>();
		for(int i = 0;i<Node.size();i++){
			ArrayList<String> ListOfTerminals = new ArrayList<String>();
			for(int j = 1;j<Terminal.size();j+=2){
				if(Terminal.get(j).contains(Node.get(i))){ //combines terminal with the same connectivity node
					ListOfTerminals.add(Terminal.get(j-1));
				}
			}
			List.add(ListOfTerminals); // Lists of all list of terminals with the same connectivity node
		}
		return List;
	}
	
	//The method joins terminals in an ArrayList based on breaker status . Also, it checks
	// the status of terminal connectivity between two group of terminals (the next method which returns True if there is a connection
	// and false if there is not).
	public ArrayList<ArrayList<String>> TerminalsInBreakers(ArrayList<ArrayList<String>> List,ArrayList<String> BreakerTerminal){
			
		for(int m = 0;m<List.size();m++){
			for(int i = 0;i<BreakerTerminal.size();i++){
			String[] T2 = BreakerTerminal.get(i).split(",");
				for(int n = m+1;n<List.size();n++){
					if(connectTerminals(List.get(m),List.get(n),T2)){
						(List.get(m)).addAll(List.get(n));
						List.remove(n);
					}
				}
			}
		}
		return List;
	}
	public boolean connectTerminals(ArrayList<String> List1,ArrayList<String> List2, String[] T2){
		
		boolean T1 = false;
		String Id = "";
		
		for(int m = 0;m<T2.length;m++){
			for(int k = 0;k<List1.size();k++){
				if(T2[m].equals(List1.get(k))){
					T1 = true;
					Id = T2[m];
					break;
				}
			}
		}
		if(T1){
			for(int m = 0;m<T2.length;m++){
				for(int j = 0;j<List2.size();j++){
					if(T2[m].equals(List2.get(j)) && Id != T2[m]){
						return true;
					}
				}
			}
		}
		return false;
	}
		
	//The Method creates a list for the 2 Terminals that are connected to all the closed breakers
	public ArrayList<String> BreakerTerminals(ArrayList<String> Breakers,ArrayList<String> Terminal){	
		ArrayList<String> BreakerTerminals = new ArrayList<String>();	
		for(int i = 0;i<Breakers.size();i++){
			String Breaker = Breakers.get(i);
			ArrayList<String> Terminalslist = new ArrayList<String>();
			for(int j = 1;j<Terminal.size();j+=2){
				if(Terminal.get(j).contains(Breaker)){
					Terminalslist.add(Terminal.get(j-1));
				}
				if(Terminalslist.size() == 2){
					BreakerTerminals.add(Terminalslist.get(0)+","+Terminalslist.get(1));
					break;
				}
			}
		}
		return BreakerTerminals;
	}
	
	
	//The method searches for the base voltage by receiving an ID for that element and the
	//VoltageElement then it searches for the correct base voltage ID and returns its value 

	public double BaseVoltageValue(String Id,ArrayList<String> BaseVoltage,ArrayList<String> VoltageElement){
		
		String VId = null;
		double baseVolt = 0;
		
		for(int i = 0;i<VoltageElement.size();i++){ 	//Searches for the ID of the base voltage 
			if((VoltageElement.get(i)).equals(Id)){
				VId = VoltageElement.get(i+1);
				break;
			}
		}
		for(int j = 0;j<BaseVoltage.size();j++){ 		//assigns the value
			if((BaseVoltage.get(j)).equals(VId)){
				baseVolt = Double.parseDouble(BaseVoltage.get(j+1));
				break;
			}
		}
		return baseVolt;
	}
	
	
	//The Method finds the terminal associate with connected lines and returns them in an ArrayList
	public ArrayList<String> LineTerminalss(String LineID,ArrayList<String> Terminal){
		ArrayList<String> elementTerminal = new ArrayList<String>();
		for(int i =0;i<Terminal.size();i++){
			if(Terminal.get(i).contains(LineID)){
				elementTerminal.add(Terminal.get(i-1));
			}
		}
		return elementTerminal;
	}
	
	
	//The Method finds the terminal associate with connected system lines and returns them in an string
	public String WindingTerminals(String WindingId,ArrayList<String> Terminal){
		String Term = null;
		for(int i =0;i<Terminal.size();i++){
			if(Terminal.get(i).contains(WindingId)){
				Term = Terminal.get(i-1);
			}
		}
		return Term;
	}
	
	//The method returns true or false for the condition that the terminals are connected to a bus
	public boolean BusConnection(String terminal,ArrayList<String> GridBusses){
		for(int i = 0;i<GridBusses.size();i++){
			if(!terminal.equals("")){
				if((GridBusses.get(i)).equals(terminal)){
					return true;
				}
			}
		}
		return false;
	}
	
	//The method calculates the line admittance and shunt admittance for transformerWinding based on the Pi Model,
	//it receives the element: W1,2 , basePower, baseVolt1,2. Then the line is modeled as line impedance (r1+jx1+r2+jx2) and
	//two shunt admittance for each winding (g1+jb1) then returned as an a array of 3.
	public CMPLX[] TransAdmittance(ReadEQfile object,String W1,String W2,double baseVolt1,double baseVolt2,double basePower) throws NegativeArraySizeException{

		double r1, x1, g1, b1, baseImp1;
		double r2, x2, g2, b2, baseImp2;
		baseImp1 = baseVolt1*baseVolt1/basePower; //Z base
		baseImp2 = baseVolt2*baseVolt2/basePower;
		r1 = object.WindingElements("cim:PowerTransformerEnd", W1, 'r')/baseImp1;
		r2 = object.WindingElements("cim:PowerTransformerEnd", W2, 'r')/baseImp2;
		x1 = object.WindingElements("cim:PowerTransformerEnd", W1, 'x')/baseImp1;
		x2 = object.WindingElements("cim:PowerTransformerEnd", W2, 'x')/baseImp2;
		b1 = object.WindingElements("cim:PowerTransformerEnd", W1, 'b')*baseImp1;
		b2 = object.WindingElements("cim:PowerTransformerEnd", W2, 'b')*baseImp2;
		g1 = object.WindingElements("cim:PowerTransformerEnd", W1, 'g')*baseImp1;
		g2 = object.WindingElements("cim:PowerTransformerEnd", W2, 'g')*baseImp2;
		CMPLX[] TransAdmittance = new CMPLX[3];
		TransAdmittance[0] = (new CMPLX((r1+r2),(x1+x2))).reciprocal();
		TransAdmittance[1] = new CMPLX(g1,b1);		
		TransAdmittance[2] = new CMPLX(g2,b2);	
		return TransAdmittance;
	}
	
	//The method calculates the line admittance and shunt admittance based on the Pi Model, it receives the element, basePower, baseVolt.
	//Then the line is modeled as line impedance (r+jx) and shunt admittance (g+jb)/2 then returned as an a array
	public CMPLX[] LineAdmittance(ReadEQfile object,String elementID,double basePower,double baseVolt) throws NegativeArraySizeException{
		
		double r, x, g, b, baseImp;
		baseImp = baseVolt*baseVolt/basePower; 		//Z base
		r = object.LineElements("cim:ACLineSegment", elementID,'r')/baseImp; // Per Unit
		x = object.LineElements("cim:ACLineSegment", elementID,'x')/baseImp;
		g = object.LineElements("cim:ACLineSegment", elementID,'g')*baseImp;
		b = object.LineElements("cim:ACLineSegment", elementID,'b')*baseImp;
		CMPLX[] LineAdmittance = new CMPLX[2];
		LineAdmittance[0] = (new CMPLX(r,x)).reciprocal();
		LineAdmittance[1] = new CMPLX(g/2.0,b/2.0);
		return LineAdmittance;
	}	
}
