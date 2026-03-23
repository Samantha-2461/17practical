/*I acknowledge the use of a large language model called Deepseek to assist me to understand what is being 
asked in the practical, debug my code and understand more about binary search trees
  */
import java.util.*;

class tNode{
 int key;
 tNode left;
 tNode right;

 tNode(int key){
  this.key=key;
  this.left=null;
  this.right=null;
 }
 public class tryBST {
  private tNode root;

  public tryBST(){
   this.root=null; 
  }
  public void insert(int key) {
   root = insertRec (root, key);
  }
  private tNode insertRec(tNode node, int key){
   if (node ==null){
    return new tNode(key);
   }
   if (key <node.key) {
    node.left = insertRec(node.left, key);
   } else if (key > node.key){
    node.right = insertRecZ(node.right, key);
   }
   return node;
   } 
  public boolean isBST(){
   return isBSTRec(root, Interger.MIN_VALUE, Interger.MAX_VALUE);
   }
  private boolean isBSTRec(tNode node, int min, int max){
   if (node ==null) {
    return true;
   }
   if (node.key <min|| node.key>max){
    return false;
   }
   return isBSTRec(node.left, min, node.key-1)&&
    isBSTRec(node.right, node.key +1, max);
  }
  public void buildBalancedBST(int n){
   int totalKeys = (int)Math.pow(2,n)-1;
   root =null;
   buildBalancedRec(1, totalKeys);
  }
  
  }
  }
 }
}
