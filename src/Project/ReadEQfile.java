package Project;

//This class was written to accomplish the following tasks: 

//1- Allow the user to import an EQ file (xml). 

//2- Parse the imported file and TempStorage information in an ArrayList.

//3- Extract nodes and attributes from parsed document

// *******************************************************************************************

//There is several Methods developed in this class which are:

// ReadEQfile(Directory) used to import and read an EQ file

// FileParse() used to parse the imported file

// createNodeList(tagName) used to  create a list of the nodes included in cim:tagName 

// extractNode(tagName, StoringArrayList) used to extract node that has tagName and save
// it on StoringArrayList

// getAttributeFromNodeChild(ChildContainsAttribute,Node) is used to get attribute is contained in node child

// LineElements(tagname,ID,LineAttribute) is used to return line r,x,g and b

// WindingElements(tagname,ID,WindingAttribute) is used to return winding r,x,g and b

// Transformer2IDWindings(TransformerID) is used to return transformer windings of a transformer with an ID 

//*******************************************************************************************


import org.w3c.dom.*;

import java.util.*;
import java.io.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ReadEQfile {
	

	protected File ImportedXML; // Set as protected since class is used in ReadSSHfile
	protected Document ParsedDocument; // Set as protected since class is used in ReadSSHfile
	protected NodeList dataList; // Set as protected since class is used in ReadSSHfile

	
	public ReadEQfile(String directory){
		ImportedXML = new File(directory);
	}
	
	public void FileParse(){
		
			try{
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				ParsedDocument = dBuilder.parse(ImportedXML);
				
				ParsedDocument.getDocumentElement().normalize();
				
		}catch(FileNotFoundException fnf){
			System.exit(0);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	

	public void createNodeList(String NodeName){
		
		String FileChecker;	
		
		try{
			FileChecker = ParsedDocument.getDocumentElement().getNodeName();
			if(FileChecker == null){
					System.out.println("The imported File is empty");
					System.exit(0);
			}
				
			dataList = ParsedDocument.getElementsByTagName(NodeName);
			
			if(dataList.getLength() == 0){
			System.out.println(NodeName + "tag name does not exists in the imported file");
			System.exit(0);
				}
				
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	public ArrayList<String> extractNode(String NodeName, ArrayList<String> ExtractedArray){
		
		
		// For each Node extraction, the following steps were done for each Node name:
		//[Step 1]: Locate the NodeName in the datalist 
		//[Step 2]: Consider each child of the node as an element
		//[Step 3]: Access desired attributes and elements then Store them in ExtractedArray
		
		
		     if (NodeName =="cim:BaseVoltage"){

		     //[Step 1]: Locate the cim:BaseVoltage in the dataList 

			for (int i = 0; i<dataList.getLength(); i++){
				
			if(dataList.item(i).getNodeType() == Node.ELEMENT_NODE){
			
		     //[Step 2]: Consider each child of cim:BaseVoltage as an element
							Element element = (Element) dataList.item(i);
										
		     //[Step 3]: Access desired values and Store them in ExtractedArray	
		     // In case of “cim:Base Voltage” ----> rdf:ID & nominal value 
							
							ExtractedArray.add(element.getAttribute("rdf:ID"));
							ExtractedArray.add(element.getElementsByTagName("cim:BaseVoltage.nominalVoltage").item(0).getTextContent());
						}
					}
			}
		     
			
			if (NodeName =="cim:Substation"){
		
		   //[Step 1]: Locate the cim:Substation in the dataList 

			for (int i = 0; i<dataList.getLength(); i++){
				
			if(dataList.item(i).getNodeType() == Node.ELEMENT_NODE){
				
		   //[Step 2]: Consider each child of cim:Substation as an element
							Element element = (Element) dataList.item(i);
							
		   //[Step 3]: Access desired values and Store them in ExtractedArray	
		   // In case of “cim:Substation” ----> rdf:ID & Name & Region_rdf:ID 
							
							ExtractedArray.add(element.getAttribute("rdf:ID"));
							ExtractedArray.add(element.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent());
		   // Since the attribute is contained in node child, getAttributeFromNodeChild is used
							ExtractedArray.add(getAttributeFromNodeChild("Substation.Region",dataList.item(i)));
						}
					}
			}
			
			
			if (NodeName =="cim:VoltageLevel"){
				
			//[Step 1]: Locate the cim:VoltageLevel in the dataList 
				
			for (int i = 0; i<dataList.getLength(); i++){
			    if(dataList.item(i).getNodeType() == Node.ELEMENT_NODE){
				
			//[Step 2]: Consider each child of cim:VoltageLevel an element
							Element element = (Element) dataList.item(i);
							
			//[Step 3]: Access desired values and Store them in ExtractedArray	
			// In case of “cim:VoltageLevel” ----> rdf:ID & Name & substation_rdf:ID & baseVoltage_rdf:ID  		
							
							ExtractedArray.add(element.getAttribute("rdf:ID"));
							ExtractedArray.add(element.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent());
			// Since the attribute is contained in node child, getAttributeFromNodeChild is used
							ExtractedArray.add(getAttributeFromNodeChild("VoltageLevel.Substation",dataList.item(i)));
							ExtractedArray.add(getAttributeFromNodeChild("VoltageLevel.BaseVoltage",dataList.item(i)));
						}
					}
					}
			
			
			if (NodeName =="cim:GeneratingUnit"){
				
			//[Step 1]: Locate the cim:GeneratingUnit in the dataList 
				
			for (int i = 0; i<dataList.getLength(); i++){
		    	if(dataList.item(i).getNodeType() == Node.ELEMENT_NODE){
		    		
			//[Step 2]: Consider each child of cim:GeneratingUnit as an element
							Element element = (Element) dataList.item(i);
							
			//[Step 3]: Access desired values and Store them in ExtractedArray	
			// In case of “cim:GeneratingUnit” ----> rdf:ID & Name & maxP & minP & equipmentContainer_rdf:ID
							
							ExtractedArray.add(element.getAttribute("rdf:ID"));
							ExtractedArray.add(element.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent());
							ExtractedArray.add(element.getElementsByTagName("cim:GeneratingUnit.maxOperatingP").item(0).getTextContent());
							ExtractedArray.add(element.getElementsByTagName("cim:GeneratingUnit.minOperatingP").item(0).getTextContent());	
			// Since the attribute is contained in node child, getAttributeFromNodeChild is used
							ExtractedArray.add(getAttributeFromNodeChild("Equipment.EquipmentContainer",dataList.item(i)));
						}
					}
					}
			
			if (NodeName =="cim:SynchronousMachine"){
				
			//[Step 1]: Locate the cim:SynchronousMachine in the dataList 
			for (int i = 0; i<dataList.getLength(); i++){
			if(dataList.item(i).getNodeType() == Node.ELEMENT_NODE){
				
		    //[Step 2]: Consider each child of cim:SynchronousMachine as an element
							Element element = (Element) dataList.item(i);
							
		    //[Step 3]: Access desired values and Store them in ExtractedArray	
		    // Note that in this case we will have data are in SSH (P & Q & baseVoltage_ rdf:ID ) 
			// Therefore, we need "Dummy" as a pointer	
							
							ExtractedArray.add("Dummy");
							
		    // In case of “cim:SynchronousMachine” ----> rdf:ID & Name & ratedS & genUnit_rdf:ID  & regControl_rdf:ID  & equipmentContainer_rdf:ID  
							ExtractedArray.add(element.getAttribute("rdf:ID"));
							ExtractedArray.add(element.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent());
							ExtractedArray.add(element.getElementsByTagName("cim:RotatingMachine.ratedS").item(0).getTextContent());
			// Since the attribute is contained in node child, getAttributeFromNodeChild is used
							ExtractedArray.add(getAttributeFromNodeChild("RotatingMachine.GeneratingUnit",dataList.item(i)));
							ExtractedArray.add(getAttributeFromNodeChild("RegulatingCondEq.RegulatingControl",dataList.item(i)));
							ExtractedArray.add(getAttributeFromNodeChild("Equipment.EquipmentContainer",dataList.item(i)));
						}
					}
					}
			
			if (NodeName =="cim:RegulatingControl"){
		    //[Step 1]: Locate the cim:RegulatingControl in the dataList 
			for (int i = 0; i<dataList.getLength(); i++){
			if(dataList.item(i).getNodeType() == Node.ELEMENT_NODE){
				
			//[Step 2]: Consider each child of cim:RegulatingControl an element
							Element element = (Element) dataList.item(i);
							
		    //[Step 3]: Access desired values and Store them in ExtractedArray	
			// Note that in this case we will have data are in SSH (targetValue) 
			// Therefore, we need "Dummy" as a pointer	
							
							ExtractedArray.add("Dummy");
							
		    // In case of “cim:RegulatingControl” ----> rdf:ID & Name   
							ExtractedArray.add(element.getAttribute("rdf:ID"));
							ExtractedArray.add(element.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent());
						}
					}
					}
			
			if (NodeName =="cim:PowerTransformer"){
				
		    //[Step 1]: Locate the cim:PowerTransformer in the dataList 
			for (int i = 0; i<dataList.getLength(); i++){
			if(dataList.item(i).getNodeType() == Node.ELEMENT_NODE){
			
			//[Step 2]: Consider each child of cim:PowerTransformer an element
						Element element = (Element) dataList.item(i);
			
						
	        //[Step 3]: Access desired values and Store them in ExtractedArray	
			// In case of “cim:PowerTransformer” ----> rdf:ID & Name & equipmentContainer_rdf:ID   
						
						ExtractedArray.add(element.getAttribute("rdf:ID"));
						ExtractedArray.add(element.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent());
			// Since the attribute is contained in node child, getAttributeFromNodeChild is used
						ExtractedArray.add(getAttributeFromNodeChild("Equipment.EquipmentContainer",dataList.item(i)));
					}
				}
				}
			
			if (NodeName =="cim:EnergyConsumer"){
				
			//[Step 1]: Locate the cim:EnergyConsumere in the dataList 
				for (int i = 0; i<dataList.getLength(); i++){
				if(dataList.item(i).getNodeType() == Node.ELEMENT_NODE){
					
			//[Step 2]: Consider each child of cim:EnergyConsumer an element
						Element element = (Element) dataList.item(i);
						
			 //[Step 3]: Access desired values and Store them in ExtractedArray	
			 // Note that in this case we will have data are in SSH (P & Q ) 
			 // Therefore, we need "Dummy" as a pointer					
						
						ExtractedArray.add("Dummy");
						
			// In case of “cim:EnergyConsumer” ----> rdf:ID & Name & equipmentContainer_rdf:ID & baseVoltage_ rdf:ID 
						ExtractedArray.add(element.getAttribute("rdf:ID"));
						ExtractedArray.add(element.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent());
			// Since the attribute is contained in node child, getAttributeFromNodeChild is used
						ExtractedArray.add(getAttributeFromNodeChild("Equipment.EquipmentContainer",dataList.item(i)));
						ExtractedArray.add(getAttributeFromNodeChild("EnergyConsumer.BaseVoltage",dataList.item(i)));
					}
				}
				}
			
			

			if (NodeName =="cim:PowerTransformerEnd"){
		
			//[Step 1]: Locate the cim:PowerTransformerEnd in the dataList 	
			for (int i = 0; i<dataList.getLength(); i++){
			if(dataList.item(i).getNodeType() == Node.ELEMENT_NODE){
			
			//[Step 2]: Consider each child of cim:PowerTransformerEnd an element
						Element element = (Element) dataList.item(i);
			
			//[Step 3]: Access desired values and Store them in ExtractedArray	
			// In case of “cim:PowerTransformerEnd” ----> rdf:ID & Name & Tr_r & Tr_x & Tr_rdf:ID & baseVoltage_ rdf:ID 
						
						ExtractedArray.add(element.getAttribute("rdf:ID"));
						ExtractedArray.add(element.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent());
						ExtractedArray.add(element.getElementsByTagName("cim:PowerTransformerEnd.r").item(0).getTextContent());
						ExtractedArray.add(element.getElementsByTagName("cim:PowerTransformerEnd.x").item(0).getTextContent());
			
			// Since the attribute is contained in node child, getAttributeFromNodeChild is used

	                    ExtractedArray.add(getAttributeFromNodeChild("TransformerEnd.Terminal",dataList.item(i)));
						ExtractedArray.add(getAttributeFromNodeChild("PowerTransformerEnd.PowerTransformer",dataList.item(i)));
						ExtractedArray.add(getAttributeFromNodeChild("TransformerEnd.BaseVoltage",dataList.item(i)));
						
			//[Note]: TransformerEnd.Terminal is used for the calculation of Y Bus	
						
						
					}
				}
				}
			

			



	if (NodeName =="cim:Breaker"){
			//[Step 1]: Locate the cim:Breaker in the dataList 
			
			for (int i = 0; i<dataList.getLength(); i++){
			if(dataList.item(i).getNodeType() == Node.ELEMENT_NODE){
				
			//[Step 2]: Consider each child of cim:Breaker an element
						Element element = (Element) dataList.item(i);
						
			//[Step 3]: Access desired values and Store them in ExtractedArray	
			// Note that in this case we will have data are in SSH (State) 
			// Therefore, we need "Dummy" as a pointer
						
						ExtractedArray.add("Dummy");
						
			// In case of “cim:Breaker” ----> rdf:ID & Name & equipmentContainer_rdf:ID & baseVoltage_ rdf:ID  
						ExtractedArray.add(element.getAttribute("rdf:ID"));
						ExtractedArray.add(element.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent());
			// Since the attribute is contained in node child, getAttributeFromNodeChild is used
						ExtractedArray.add(getAttributeFromNodeChild("Equipment.EquipmentContainer",dataList.item(i)));
					}
				}
				}
			
			
			if (NodeName =="cim:RatioTapChanger"){
				
			//[Step 1]: Locate the cim:RatioTapChanger in the dataList 
			for (int i = 0; i<dataList.getLength(); i++){
			if(dataList.item(i).getNodeType() == Node.ELEMENT_NODE){
			
			//[Step 2]: Consider each child of cim:RatioTapChanger an element
						Element element = (Element) dataList.item(i);
						
			//[Step 3]: Access desired values and Store them in ExtractedArray	
			// Note that in this case we will have data are in SSH (step) 
			// Therefore, we need "Dummy" as a pointer	
						
				       ExtractedArray.add("Dummy");
						
			// In case of “cim:RatioTapChanger” ----> rdf:ID & Name 

				        ExtractedArray.add(element.getAttribute("rdf:ID"));
						ExtractedArray.add(element.getElementsByTagName("cim:IdentifiedObject.name").item(0).getTextContent());
						
			//[Note]: RatioTapChanger.TransformerEnd is used for the calculation of Y Bus	
			// Since the attribute is contained in node child, getAttributeFromNodeChild is used
						ExtractedArray.add(getAttributeFromNodeChild("RatioTapChanger.TransformerEnd",dataList.item(i)));
					}
				}
				}
			
			
				//[Note] cim:ACLineSegment is used for the Y Bus matrix calculations
			
			if (NodeName =="cim:ACLineSegment"){
			    //[Step 1]: Locate the cim:ACLineSegment in the dataList
				
				for (int i = 0; i<dataList.getLength(); i++){
				if(dataList.item(i).getNodeType() == Node.ELEMENT_NODE){
					
				//[Step 2]: Consider each child of cim:ACLineSegmente an element
						Element element = (Element) dataList.item(i);
				
				//[Step 3]: Access desired values and Store them in ExtractedArray	
				// In case of “cim:ACLineSegment” ----> rdf:ID & Name & Basevoltage 
						
						ExtractedArray.add(element.getAttribute("rdf:ID"));
				
				//[Note]: rdf:ID & ConductingEquipment.BaseVoltage are used for the calculation of lines PU values	
				// Since the attribute is contained in node child, getAttributeFromNodeChild is used
						ExtractedArray.add(getAttributeFromNodeChild("ConductingEquipment.BaseVoltage",dataList.item(i)));
					}
				}
				}

			
			//[Note] cim:Terminal is used for the Y Bus matrix calculations
			
			if (NodeName =="cim:Terminal"){
			 //[Step 1]: Locate the cim:BaseVoltage in the dataList 
				
			for (int i = 0; i<dataList.getLength(); i++){
			if(dataList.item(i).getNodeType() == Node.ELEMENT_NODE){
			 
			//[Step 2]: Consider each child of cim:BaseVoltage an element
						Element element = (Element) dataList.item(i);
						
			//[Step 3]: Access desired values and Store them in ExtractedArray	
			// In case of “cim:Terminal” ----> rdf:ID & Name & Basevoltage 		
						ExtractedArray.add(element.getAttribute("rdf:ID"));
			// Since the attribute is contained in node child, getAttributeFromNodeChild is used
						ExtractedArray.add(""+getAttributeFromNodeChild("Terminal.ConductingEquipment",dataList.item(i))+","+getAttributeFromNodeChild("Terminal.ConnectivityNode",dataList.item(i))+"");
			
			//[Note]: Terminal.ConductingEquipment & Terminal.ConnectivityNode are used for the calculation of Y matrix

					}
				}
				}
		
			//[Note] cim:ConnectivityNode is used for the Y Bus matrix calculations
			
			if (NodeName =="cim:ConnectivityNode"){
			 //[Step 1]: Locate the cim:ConnectivityNode in the dataList 
				
			for (int i = 0; i<dataList.getLength(); i++){
			if(dataList.item(i).getNodeType() == Node.ELEMENT_NODE){
				
			 //[Step 2]: Consider each child of cim:ConnectivityNode an element
						Element element = (Element) dataList.item(i);
						
			 //[Step 3]: Access desired values and Store them in ExtractedArray
						ExtractedArray.add(element.getAttribute("rdf:ID"));
					}
				}
				}

			return ExtractedArray;
		}

	
	
	// This Method is used to get attribute is contained in node child

	
	public String getAttributeFromNodeChild(String SelectedElement, Node SelectedNode){
		

	
		
		String Requested_Id = "";
		String Requested_Name = "";
		String FirstID = "";
		String SecondID = "";
		
		boolean StringNameExists = false;
		//StringNameExists is used as a flag
		//When True --> the string name exists in the node child (Connected Directly)
		//When False -->  the string name is not directly exists in the node child (Further Analysis)
		
		if (SelectedNode != null && SelectedNode.hasChildNodes()){
			
		//Sweep through node childrens to locate the requested attribute
			
			for(int i = 0;i<SelectedNode.getChildNodes().getLength();i++){
				
				if(SelectedNode.getChildNodes().item(i).hasAttributes()){
					
					if(SelectedNode.getChildNodes().item(i).getNodeType() == Node.ELEMENT_NODE){
					
					Element reqElement = (Element) SelectedNode.getChildNodes().item(i);
					
					Requested_Name = reqElement.getTagName().replace("cim:","");
					
					if(Requested_Name.equals(SelectedElement)){
	
						FirstID = reqElement.getAttribute("rdf:resource").replace("#","");
						
						StringNameExists = true;	
						
						for(int m = 0;m<ParsedDocument.getDocumentElement().getChildNodes().getLength();m++){
							
							if(ParsedDocument.getDocumentElement().getChildNodes().item(m).hasAttributes()){
								if(ParsedDocument.getDocumentElement().getChildNodes().item(m).getNodeType() == Node.ELEMENT_NODE){
									Element ChildElement = (Element) ParsedDocument.getDocumentElement().getChildNodes().item(m);
									if(ChildElement.getAttribute("rdf:ID").equals(FirstID)){

									}
								}
							}
						}
						
						//No further analysis is required hence StringNameExists set to true
					}
					
					
					// Further analysis is counducted 

					else if(!Requested_Name.equals(SelectedElement)){
	
							String[] parts = SelectedElement.split("\\.");
							Requested_Id = reqElement.getAttribute("rdf:resource").replace("#","");
							
							if(ParsedDocument!= null && ParsedDocument.getDocumentElement().hasChildNodes()){
			
								for(int n = 0;n<ParsedDocument.getDocumentElement().getChildNodes().getLength();n++){
								
									if(ParsedDocument.getDocumentElement().getChildNodes().item(n).hasAttributes()){
										if(ParsedDocument.getDocumentElement().getChildNodes().item(n).getNodeType() == Node.ELEMENT_NODE){
											
											Element RootChildElement = (Element) ParsedDocument.getDocumentElement().getChildNodes().item(n);
											
											//Use childern rdf:ID in the selected node to  find  its parent nodes
											
											if(RootChildElement.getAttribute("rdf:ID").equals(Requested_Id)){
												
												if(RootChildElement!= null && RootChildElement.hasChildNodes()){
													
													NodeList SelectedNode_FA = RootChildElement.getChildNodes();
													
													for(int l = 0;l<SelectedNode_FA.getLength();l++){
														
														if(SelectedNode_FA.item(l).hasAttributes()){
															if(SelectedNode_FA.item(l).getNodeType() == Node.ELEMENT_NODE){
																
																Element NodeElementFA = (Element) SelectedNode_FA.item(l);
																if(NodeElementFA.getTagName().contains(parts[1])){
																	SecondID = NodeElementFA.getAttribute("rdf:resource").replace("#","");
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		//Returns either of IDs if it exists directly in the node or not
		if(StringNameExists == true){
			return FirstID;
		}else
			return SecondID;
	}
	
	
	//This Method is used to return  elements  of a line (r,x,g and b) with tagname,ID and attribute  	
	
public double LineElements(String tagname,String ID,char LineAttribute){
		
		String LineElement = null;
		
		try{
			dataList = ParsedDocument.getElementsByTagName(tagname);
				if(dataList.getLength() == 0){
					System.out.println("No such tag name exists in file");
				}
				
		}catch(Exception element){
			element.printStackTrace();
		}
		
		if(LineAttribute == 'r' || LineAttribute == 'x' || LineAttribute == 'g' || LineAttribute == 'b'){
			
		if (tagname == "cim:ACLineSegment") {
				for (int i = 0; i<dataList.getLength(); i++){
					
					if(dataList.item(i).getNodeType() == Node.ELEMENT_NODE){
						
						Element element = (Element) dataList.item(i);
						if(element.getAttribute("rdf:ID").equals(ID)){
						
						switch (LineAttribute){
						
						case 'r' :
						
							return Double.parseDouble(element.getElementsByTagName("cim:ACLineSegment.r").item(0).getTextContent());
						
						case 'x' :
	
							return Double.parseDouble(element.getElementsByTagName("cim:ACLineSegment.x").item(0).getTextContent());
						
						case 'g' :

							return Double.parseDouble(element.getElementsByTagName("cim:ACLineSegment.gch").item(0).getTextContent());
						
						case 'b' :
		
							return Double.parseDouble(element.getElementsByTagName("cim:ACLineSegment.bch").item(0).getTextContent());
						
						}
							
							
						}
					}
				}
			
		
			} else {
				System.out.println("Error while getting line elements");
				
			}
			}
		return Double.parseDouble(LineElement);
		}


//This Method is used to return transformer element windings of a transformer (r,x,g and b) with tagname,ID and attribute  	

public double WindingElements(String tagname,String ID,char WindingAttribute){
	
	String WindingElement = null;
	
	try{
		dataList = ParsedDocument.getElementsByTagName(tagname);
			if(dataList.getLength() == 0){
				System.out.println("No such tag name exists in file");
			}
			
	}catch(Exception element){
		element.printStackTrace();
	}
	
	if(WindingAttribute == 'r' || WindingAttribute == 'x' || WindingAttribute == 'g' || WindingAttribute == 'b'){
		
	if (tagname == "cim:PowerTransformerEnd") {
			for (int i = 0; i<dataList.getLength(); i++){
				
			
				if(dataList.item(i).getNodeType() == Node.ELEMENT_NODE){
					
					Element element = (Element) dataList.item(i);
					if(element.getAttribute("rdf:ID").equals(ID)){
					
					switch (WindingAttribute){
					
					case 'r' :
					
						return Double.parseDouble(element.getElementsByTagName("cim:PowerTransformerEnd.r").item(0).getTextContent());
					
					case 'x' :

						return Double.parseDouble(element.getElementsByTagName("cim:PowerTransformerEnd.x").item(0).getTextContent());
					
					case 'g' :
					
						return Double.parseDouble(element.getElementsByTagName("cim:PowerTransformerEnd.g").item(0).getTextContent());
					
					case 'b' :

						return Double.parseDouble(element.getElementsByTagName("cim:PowerTransformerEnd.b").item(0).getTextContent());
					
					}
						
						
					}
				}
			}
		
	
		} else {
			System.out.println("Error while getting winding elements");
			
		}
		}
	return Double.parseDouble(WindingElement);
	}



	
	
	//This Method is used to return transformer windings of a transformer with an ID  	
	
	public ArrayList<String> TransformerID2Windings(String TransformerID){
		
		ArrayList<String> TransWindings = new ArrayList<String>();
		
		try{
			dataList = ParsedDocument.getElementsByTagName("cim:PowerTransformerEnd");
			
			if(dataList.getLength() == 0){
				
				System.out.println("No such tag name exists in file");
			}
			
			for (int i = 0; i<dataList.getLength(); i++){
				
				if(dataList.item(i).getNodeType() == Node.ELEMENT_NODE){
					
					Element element = (Element) dataList.item(i);
					
					if((getAttributeFromNodeChild("PowerTransformerEnd.PowerTransformer",dataList.item(i))).equals(TransformerID)){
						
						TransWindings.add(element.getAttribute("rdf:ID"));
					}
				}
			}
				
		}catch(Exception element){
			element.printStackTrace();
		}
		
		return TransWindings;
	}
	
}