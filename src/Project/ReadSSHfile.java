package Project;

//This class was written to accomplish the following tasks: 

//1- Allow the user to import an SSH file (xml). 

//2- Parse the imported file and TempStorage information in an ArrayList.

//3- Extract nodes and attributes from parsed document

//*******************************************************************************************

// This class include extractNode SSH which follows the same structure of extractNode 
// introduced in ReadEQfile class

//*******************************************************************************************

// For each Node extraction, the following steps were done for each Node name:
//[Step 1]: Locate the nodeName in the datalist 
//[Step 2]: Consider each child of the node as an element
//[Step 3]: Access desired attributes and elements then add them to ExtractedArray


//*******************************************************************************************


import java.util.ArrayList;

import org.w3c.dom.Element;


public class ReadSSHfile extends ReadEQfile{

	public ReadSSHfile(String dir){
		
		super(dir);
	
	}
	

	@SuppressWarnings("static-access")
	public ArrayList<String> extractNodeSSH(String NodeName, ArrayList<String> ExtractedArray){
		
		
		if (NodeName == "cim:SynchronousMachine"){
			
		  	//[Step 1]: Locate the nodeName in the datalist 
			for (int i = 0; i<dataList.getLength(); i++){
			if(dataList.item(i).getNodeType() == dataList.item(i).ELEMENT_NODE){
			
			//[Step 2]: Consider each child of the node as an element
					Element element = (Element) dataList.item(i);
		
		    // Note that in this if (NodeName ==  we will have data are in EQ 
			// Therefore, we need "Dummy" as a pointer			
					ExtractedArray.add("Dummy");	
					
			//[Step 3]: Access desired attributes and elements then add them to ExtractedArray
			//In this case desired attributes and elements are P, Q and baseVoltage_ rdf:ID 
					
					ExtractedArray.add(element.getAttribute("rdf:about").replace("#",""));
					ExtractedArray.add(element.getElementsByTagName("cim:RotatingMachine.p").item(0).getTextContent());
					ExtractedArray.add(element.getElementsByTagName("cim:RotatingMachine.q").item(0).getTextContent());
				}
			}
		}
		
		
		if (NodeName ==  "cim:RegulatingControl") {
			
		  	//[Step 1]: Locate the nodeName in the datalist 
			for (int i = 0; i<dataList.getLength(); i++){
			if(dataList.item(i).getNodeType() == dataList.item(i).ELEMENT_NODE){
					
			//[Step 2]: Consider each child of the node as an element
					Element element = (Element) dataList.item(i);
					
		    // Note that in this if (NodeName ==  we will have data are in EQ 
			// Therefore, we need "Dummy" as a pointer			
					ExtractedArray.add("Dummy");
					
			//[Step 3]: Access desired attributes and elements then add them to ExtractedArray
			//In this case desired attributes and elements are rdf:about and targetValue
					ExtractedArray.add(element.getAttribute("rdf:about").replace("#",""));
					ExtractedArray.add(element.getElementsByTagName("cim:RegulatingControl.targetValue").item(0).getTextContent());
				}
			}
			}
		
		
		if (NodeName ==  "cim:EnergyConsumer") {
			
		  	//[Step 1]: Locate the nodeName in the datalist 
			for (int i = 0; i<dataList.getLength(); i++){
			if(dataList.item(i).getNodeType() == dataList.item(i).ELEMENT_NODE){
				
			//[Step 2]: Consider each child of the node as an element
					Element element = (Element) dataList.item(i);
					
			// Note that in this if (NodeName ==  we will have data are in EQ 
			// Therefore, we need "Dummy" as a pointer			
					ExtractedArray.add("Dummy");
					
			//[Step 3]: Access desired attributes and elements then add them to ExtractedArray
			//In this case desired attributes and elements are rdf:about, P and Q
					ExtractedArray.add(element.getAttribute("rdf:about").replace("#",""));
					ExtractedArray.add(element.getElementsByTagName("cim:EnergyConsumer.p").item(0).getTextContent());
					ExtractedArray.add(element.getElementsByTagName("cim:EnergyConsumer.q").item(0).getTextContent());
				}
			}
			}
		
		
		if (NodeName ==  "cim:Breaker") {
			
		  	//[Step 1]: Locate the nodeName in the datalist 
			for (int i = 0; i<dataList.getLength(); i++){
			if(dataList.item(i).getNodeType() == dataList.item(i).ELEMENT_NODE){
					
			
			//[Step 2]: Consider each child of the node as an element
					Element element = (Element) dataList.item(i);
					
			// Note that in this if (NodeName ==  we will have data are in EQ 
			// Therefore, we need "Dummy" as a pointer			
					ExtractedArray.add("Dummy");
					
			//[Step 3]: Access desired attributes and elements then add them to ExtractedArray
			//In this case desired attributes and elements are rdf:about and switch status
					ExtractedArray.add(element.getAttribute("rdf:about").replace("#",""));
					ExtractedArray.add(element.getElementsByTagName("cim:Switch.open").item(0).getTextContent());
				}
			}
			}
		
		
		if (NodeName ==  "cim:RatioTapChanger") {
			
		  	//[Step 1]: Locate the nodeName in the datalist 
			for (int i = 0; i<dataList.getLength(); i++){
			if(dataList.item(i).getNodeType() == dataList.item(i).ELEMENT_NODE){
					
			//[Step 2]: Consider each child of the node as an element

					Element element = (Element) dataList.item(i);
			
			// Note that in this if (NodeName ==  we will have data are in EQ 
			// Therefore, we need "Dummy" as a pointer	
					ExtractedArray.add("Dummy");
					
			//[Step 3]: Access desired attributes and elements then add them to ExtractedArray
			//In this case desired attributes and elements are rdf:about and step

					ExtractedArray.add(element.getAttribute("rdf:about").replace("#",""));
					ExtractedArray.add(element.getElementsByTagName("cim:TapChanger.step").item(0).getTextContent());
				}
			}
			}
		
		
		//[Note] cim:Terminal is used for the Y Bus matrix calculations
		
		if (NodeName ==  "cim:Terminal") {
			
		  	//[Step 1]: Locate the nodeName in the datalist 
			for (int i = 0; i<dataList.getLength(); i++){
			if(dataList.item(i).getNodeType() == dataList.item(i).ELEMENT_NODE){
					
					Element element = (Element) dataList.item(i);
					
			    //[Step 3]: Access desired attributes and elements then add them to ExtractedArray
				//In this case desired attributes and elements are rdf:about and Terminal Connection status

					ExtractedArray.add(element.getAttribute("rdf:about").replace("#",""));
					ExtractedArray.add(element.getElementsByTagName("cim:ACDCTerminal.connected").item(0).getTextContent());
				}
			}
			}
		

		return ExtractedArray;
	}
}
