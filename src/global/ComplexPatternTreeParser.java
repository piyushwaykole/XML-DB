package global;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import heap.Tuple;
import index.IndexScan;
import iterator.NestedLoopsJoins;
import iterator.QueryPlanExecutor;
import iterator.RelSpec;
import iterator.SortMerge;
import iterator.CondExpr;
import iterator.FileScan;
import iterator.FldSpec;
import iterator.Iterator;



public class ComplexPatternTreeParser {
    
    private SimplePatternTreeParser spt1;
    private SimplePatternTreeParser spt2;
    private String operation;
    private int buf_size;
    final String HEAPFILENAME = "xml.in";

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getBuf_size() {
        return buf_size;
    }

    public void setBuf_size(int buf_size) {
        this.buf_size = buf_size;
    }

 
    public ComplexPatternTreeParser(String inputPath) {
        BufferedReader br;
        
        try {
            Path path = Paths.get(inputPath);
            long lineCount = Files.lines(path).count();
            String ptPath1;
            br = new BufferedReader(new FileReader(inputPath));
            
            ptPath1 = br.readLine();
            this.spt1 = new SimplePatternTreeParser(ptPath1.trim());
            
            if(lineCount != 4 && lineCount != 3) {
                System.out.println("Error: Invalid Complex pattern tree format");
                br.close();
                return;
            }
            
            if (lineCount == 4) {
                String ptPath2;              
                ptPath2 = br.readLine();
                this.spt2 = new SimplePatternTreeParser(ptPath2.trim());
                
            } 
            this.operation = br.readLine().trim();

            
            buf_size = Integer.parseInt(br.readLine().trim());
            br.close();
            
        }catch (FileNotFoundException | NoSuchFileException e ) {
            System.out.println("Error: Invalid file path in pattern tree");
            return;
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }
    }
    
    public void execute(int query_plan) {
        QueryPlanExecutor query = new QueryPlanExecutor();
        QueryPlanExecutor query2 = new QueryPlanExecutor();

        iterator.Iterator it = null;
        Iterator it2, it3, it4, it5, it6;
              

        it = query.QueryPlanExecutor1(spt1.getMap(), spt1.getConditions(), spt1.getInl(),0,HEAPFILENAME,-1);
        System.out.println("Staring query 2");
        it2 = query2.QueryPlanExecutor1(spt2.getMap(), spt2.getConditions(), spt2.getInl(),0,HEAPFILENAME,-1);

        System.out.println("Ending query 2");
//        it3 = query2.QueryPlanExecutor2(spt2.getMap(), spt2.getConditions(), spt2.getInl(),0,HEAPFILENAME,-1);
//        it4 = query.QueryPlanExecutor2(spt1.getMap(), spt1.getConditions(), spt1.getInl(),0,HEAPFILENAME,-1);
//
//        it5 = query2.QueryPlanExecutor2(spt2.getMap(), spt2.getConditions(), spt2.getInl(),0,HEAPFILENAME,-1);
//        it6 = query.QueryPlanExecutor2(spt1.getMap(), spt1.getConditions(), spt1.getInl(),0,HEAPFILENAME,-1);

        NestedLoopsJoins nlj = (NestedLoopsJoins)it;
        int sizeofTuple = nlj.getFinalTupleSize();
        
        AttrType []  outputtype = new AttrType[sizeofTuple];
          
        for(int i=0;i< (sizeofTuple/3);i++) {
            outputtype[i*3]= new AttrType(AttrType.attrInterval);
            outputtype[i*3+1]=new AttrType(AttrType.attrInteger);
            outputtype[i*3+2]=new AttrType(AttrType.attrString);
              
          }
      
//        Tuple t;
//          t = null;
//          try {
//            while ((t = it.get_next()) != null) {
//              t.print(outputtype);
//            
//            }
//          }
//          catch (Exception e) {
//            System.err.println (""+e);
//            e.printStackTrace();
//            Runtime.getRuntime().exit(1);
//          }
//          
//          
//
//          System.out.println ("\n"); 
//          try {
//            nlj.close();
//          }
//          catch (Exception e) {
//          
//            e.printStackTrace();
//          }
           
            NestedLoopsJoins nlj2 = (NestedLoopsJoins)it2;
            int sizeofTuple2 = nlj2.getFinalTupleSize();
            
            AttrType []  outputtype2 = new AttrType[sizeofTuple2];
              
            for(int i=0;i< (sizeofTuple2/3);i++) {
                outputtype2[i*3]= new AttrType(AttrType.attrInterval);
                outputtype2[i*3+1]=new AttrType(AttrType.attrInteger);
                outputtype2[i*3+2]=new AttrType(AttrType.attrString);
                  
              }
            
//            t = null;
//            try {
//              while ((t = it2.get_next()) != null) {
//                t.print(outputtype2);
//              
//              }
//            }
//            catch (Exception e) {
//              System.err.println (""+e);
//              e.printStackTrace();
//              Runtime.getRuntime().exit(1);
//            }

          String[] operationSplit = operation.split("\\s+");
          
          
//          try {
//          Tuple t;
//          t = null;
//          while ((t = it.get_next()) != null) {
//              t.print(outputtype);
//            
//            }
//          System.out.println("\n\n");
//          
//          while ((t = it2.get_next()) != null) {
//              t.print(outputtype2);
//            
//            }
//          } catch (Exception e) {
//              System.err.println (""+e);
//            e.printStackTrace();
//            Runtime.getRuntime().exit(1);
//          }
          

          
        
          switch(operationSplit[0]) {
              case "CP":
                  
                  break;
              case "NJ":
                  CP();
                  break;
              case "TJ":
                  
                  String iTag = operationSplit[1];
                  String jTag = operationSplit[2];
                  int joinColumn1 = 0;
                  int joinColumn2 = 0;
                  for (Map.Entry<Integer, String> entry : query.getDynamic().entrySet()) {
                      String value = entry.getValue();
                      if (iTag.equals(value)) {
                          joinColumn1 = (entry.getKey())*3+2;
                          break;
                      }
                  }
                  for (Map.Entry<Integer, String> entry : query2.getDynamic().entrySet()) {
                      String value = entry.getValue();
                      if (jTag.equals(value)) {
                          joinColumn2 = (entry.getKey())*3+2;
                          break;
                      }
                  }
                  TJ(outputtype, outputtype2, it, it2, joinColumn1+1, joinColumn2+1 );
                  break;
              case "SRT":
                  SRT();
                  break;
              case "GRP":
                  GRP();
                  break;
          }
    }
    
    public void CP() {
            
        }
        
    public void TJ() {
        
    }
    
    public void TJ(AttrType []  ltypes, AttrType []  rtypes, iterator.Iterator it1, iterator.Iterator it2, int joinColumn1, int joinColumn2 ) {
        try {
  
            TupleOrder ascending = new TupleOrder(TupleOrder.Ascending);

            short []   lsizes = new short[((ltypes.length)/3)];
            for(int j=0;j<(ltypes.length)/3;j++)
                lsizes[j]=10;
            
            short []   rsizes = new short[((rtypes.length)/3)];
            for(int j=0;j<(rtypes.length)/3;j++)
                rsizes[j]=10;
            
            
            CondExpr [] outFilter = new CondExpr[3];
            outFilter[0] = new CondExpr();
            outFilter[1] = null;
            
            
            outFilter[0].next  = null;
            outFilter[0].op    = new AttrOperator(AttrOperator.aopEQ);
            outFilter[0].type1 = new AttrType(AttrType.attrSymbol);
            outFilter[0].type2 = new AttrType(AttrType.attrSymbol);
            outFilter[0].operand1.symbol = new FldSpec (new RelSpec(RelSpec.outer),joinColumn1);
            outFilter[0].operand2.symbol = new FldSpec (new RelSpec(RelSpec.innerRel),joinColumn2);
            outFilter[0].flag=0;
            
            
            FldSpec []  proj1 = new FldSpec[ltypes.length + rtypes.length-3];

            for(int i=0;i< ltypes.length;i++) {
                proj1[i]=new FldSpec(new RelSpec(RelSpec.outer), 1+i);
               
           }
            int x = ltypes.length;
            for(int i=1;i<= (rtypes.length/3);i++) {
                if((joinColumn2/3) != i) {
                    proj1[x++]=new FldSpec(new RelSpec(RelSpec.innerRel), (i-1)*3+1);
                    proj1[x++]=new FldSpec(new RelSpec(RelSpec.innerRel), (i-1)*3+2);
                    proj1[x++]=new FldSpec(new RelSpec(RelSpec.innerRel), (i-1)*3+3);

                }               
           }
            
            //System.out.println("recursion");
            
          
            //System.out.println(joinColumn);
            SortMerge sm = new SortMerge(ltypes, ltypes.length, lsizes,
                      rtypes, rtypes.length, rsizes,
                      joinColumn1, 10, 
                      joinColumn2, 10, 
//                      (ltypes.length + rtypes.length)/3*10,
                      20,
                      it1, it2, 
                      false, false, ascending,
                      outFilter, proj1, (ltypes.length + rtypes.length-3));
        
            
            Tuple t;
            t = null;
            List<AttrType> both = new ArrayList<AttrType>(ltypes.length + rtypes.length-3);
            Collections.addAll(both, ltypes);
            Collections.addAll(both, rtypes);
            
            
          t = null;
          try {
            while ((t = sm.get_next()) != null) {
              t.print(both.toArray(new AttrType[both.size()]));
            
            }
          }
          catch (Exception e) {
            System.err.println (""+e);
            e.printStackTrace();
            Runtime.getRuntime().exit(1);
          }
//            while ((t = sm.get_next()) != null) {
//                t.print((both.toArray(new AttrType[both.size()])));
//              
//              }
            
            sm.close();
      } 
      catch (Exception e) {
          System.err.println ("*** Error preparing for sm_join");
          System.err.println (""+e);
          e.printStackTrace();
          Runtime.getRuntime().exit(1);
      }
        
        
    }
    
    public void SRT() {
        
    }

    public void GRP() {
        
    }
    

}
