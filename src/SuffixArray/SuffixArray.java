package SuffixArray;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by belisariops on 6/5/17.
 */


public class SuffixArray {
    int[] array;
    StringBuilder s;

    public SuffixArray(StringBuilder s) {
        int n = s.length();
        if (s.length()>=2)
            s.append("$$$");
        this.s=s;
        int arraySize = s.length();
        int [] text = new int[arraySize];


        for (int i =0; i< arraySize;i++)
            text[i] = this.toInt(s.charAt(i));

        array = this.constructArray(text,n,26);
        this.s = new StringBuilder(s.substring(0,s.length()-3));

    }

    private void buildSuffixArray() {

        List<Tripla> a = new ArrayList<Tripla>();
        List<Tripla> b = new ArrayList<Tripla>();
        List<Tripla> c = new ArrayList<Tripla>();
        /*PCA Rulz*/
        int i;
        for (i=1;i<s.length() - 2;i+=3) {
            a.add(new Tripla(i,i,true));
            b.add(new Tripla(i+1,i+1,false));
            c.add(new Tripla(i+2,i+2,false));
        }
        /*Ordenamos lexicograficamente los arreglos con Radix Sort*/
        a = radixSort(a,s);
        b = radixSort(b,s);
        /*Se obtiene el ultimo valor del la ultima tripla, para que todos los de b sean mayor a este*/
        int lexOrder=a.get(a.size()-1).getLexNaming()+2;
        System.out.println(a.get(0).getLexNaming());
        StringBuilder aux = new StringBuilder();
        for (Tripla t: a) {
            System.out.println("aaaa "+t.getStart());
            aux.append((char)(t.getLexNaming()+97));
        }
        for (Tripla t : b) {
            t.setLexNaming(t.getLexNaming()+lexOrder);
            aux.append((char)(t.getLexNaming()+97));
        }

        System.out.println(aux);

        List<Tripla> ab = new ArrayList<Tripla>(a);
        ab.addAll(b);




    }


    /**
     * Convierten un caracter en un entero, para asi no agotar los caracteres ascii.
     * @param c un caracter
     * @return int representando al caracter
     */
    private int toInt(char c) {
        if (c == '$')
            return 0;
        return (int)c-96;

    }


    /**
     * Radix Sort de una lista de triplas de caracteres en un alfabeto,
     * usando counting sort.
     * @param list de triplas que apuntan a indices de un texto
     * @return list ordenada por prefijo
     */
     private ArrayList<Tripla> radixSort(List<Tripla> list,StringBuilder s) {
         ArrayList<Tripla>[] buckets = new ArrayList[27];

         for (int c=0; c<27; c++)
             buckets[c] = new ArrayList<Tripla>();

         List<Tripla> resp = new ArrayList<Tripla>(list);

         ArrayList<Tripla> ret = new ArrayList<Tripla>();
         int lexOrder=0;
        for (int i=2; i>=0; i--) {

            for (Tripla t : resp) {
                try {
                    buckets[s.charAt(t.getStart()) - 96].add(t);
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    buckets[0].add(t);
                }
            }

            ret.clear();
            lexOrder = 0;
            for (ArrayList<Tripla> array: buckets) {
                boolean count=false;
                for (Tripla t:array) {
                    count = true;
                    t.setLexNaming(lexOrder);
                    ret.add(0,t);
                }
                if (count)
                    lexOrder++;
                array.clear();

            }

        }

        return ret;
     }


    /**
     * Recive un texto representado con enteros y el tamaño del alfabeto, retorna el arreglo de sufijos (como
     * arreglo de enteros).
     * @param text representado por un arreglo de enteros
     * @param alphabet cantidad de letras en el alfabeto, exceptuando el caracter 0 ($)
     * @return
     */
     private int[] constructArray(int[] text,int n,int alphabet) {
         int []SA = new int[text.length];
         int n0 = (n+2)/3;
         int n1 = (n+1)/3;
         int n2 = (n)/3;
         int n02 = n0+n2;

         int[] s12 = new int[n02+3];
         s12[n02+2]=s12[n02+1]=s12[n02]=0;
         int[] SA12 = new int[n02+3];
         SA12[n02+2]=SA12[n02+1]=SA12[n02]=0;
         int [] s0 = new int[n0];
         int [] SA0 = new int[n0];

         for (int i=0,j=0; i< n+(n0-n1);i++) {
             if ((i%3)!=0)
                s12[j++]=i;
         }

         SA12 = sort(s12, Arrays.copyOfRange(text,2,text.length),n02,alphabet);
         s12 = sort(SA12, Arrays.copyOfRange(text,1,text.length),n02,alphabet);
         SA12 = sort(s12, Arrays.copyOfRange(text,0,text.length),n02,alphabet);

         int name=0,c0=-1,c1=-1,c2=-1;
         for (int i=0;i<n02;i++) {
             if (text[SA12[i]] != c0 || text[SA12[i]+1]!=c1 || text[SA12[i]+2]!=c2) {
                 name++;
                 c0 = text[SA12[i]];
                 c1 = text[SA12[i]+1];
                 c2 = text[SA12[i]+2];
             }

             if (SA12[i]%3 ==1) {
                 s12[SA12[i]/3] = name;
             }
             else
                 s12[SA12[i]/3 +n0] = name;
         }

         if (name <n02) {
             SA12 = constructArray(s12,n02,name);
             for (int k = 0; k<n02; k++) {
                 s12[SA12[k]] = k+1;
             }

         }
         else
             for (int i =0; i<n02;i++)
                 SA12[s12[i]-1] =i;


         for (int i=0,j=0;i<n02;i++)
             if(SA12[i]<n0)
                 s0[j++] = 3*SA12[i];

         SA0 = sort(s0,text,n0,alphabet);

         for (int p=0,t=n0-n1,k=0;k<n;k++) {
             int i = ((SA12[t] < n0) ? ((SA12[t] * 3) + 1) : (((SA12[t] - n0) * 3) + 2));
             int j = SA0[p];

             if (SA12[t]<n0? lexPairs(text[i],s12[SA12[t]+n0],text[j],s12[j/3]):
                     lexTriples(text[i],text[i+1],s12[SA12[t]-n0+1],text[j],text[j+1],s12[j/3+n0])) {
                 SA[k] = i;
                 t++;
                 if (t == n02)
                     for (k++;p<n0;p++,k++)
                         SA[k]=SA0[p];

             }
             else {
                 SA[k] =j;
                 p++;
                 if (p==n0)
                     for (k++; t<n02; t++,k++)
                         SA[k] = ((SA12[t] < n0) ? ((SA12[t] * 3) + 1) : (((SA12[t] - n0) * 3) + 2));
             }


         }

         return SA;












     }

     private int[] sort(int []a,int[] array,int n,int buckets) {
         int [] ret = new int[a.length];
         int[] c = new int[buckets+1];
         int i;
         for (i=0; i<=buckets;i++)
             c[i] = 0;
         for (i=0;i<n;i++)
             c[array[a[i]]]++;
         int sum=0;
         for (i=0;i<=buckets;i++) {
             int t = c[i];
             c[i] = sum;
             sum+=t;
         }

         for (i=0;i<n;i++)
             ret[c[array[a[i]]]++]=a[i];
         return ret;
     }

     private boolean lexPairs(int a1,int a2, int b1, int b2) {
         return (a1<b1 || a1==b1 && a2<=b2);
     }

     private boolean lexTriples(int a1,int a2,int a3, int b1, int b2,int b3) {
         return (a1<b1 || a1==b1 && lexPairs(a2,a3,b2,b3));
     }


    /**
     * Esta funcion encuentra las posiciones en el texto donde se puede encontrar el  patron, haciendo busqueda binaria
     * en el suffixArray y en cada busqueda binaria comparando el patron con el sufijo, caracter a caracter.
     * @param pattern
     * @return Lista de enteros que representan las posiciones donde se puede encontrar el patron.
     */
     public List<Integer> findPattern(String pattern) {
         List<Integer> resp = new ArrayList<Integer>();
         char[] patternArray = pattern.toCharArray();

         int l = 0, r=this.array.length-1;
         while (r>l) {
             int m = (l+r)/2 + ((l+r)%2 ==0 ? 0:1);
             if (m== r || l ==m)
                 break;
             String element = s.substring(this.array[m]);
             char[] arrayElement = element.toCharArray();
             boolean flag = true;

             if (patternArray.length > arrayElement.length) {
                 flag = false;
             }

             else {
                 for (int i = 0; i < patternArray.length; i++) {
                     if (patternArray[i] != arrayElement[i]) {
                         flag = false;
                         break;
                     }

                 }
             }
             if (flag) {
                 resp.add(this.array[m]);
                 //Aca falta algo

             }

             int compare = pattern.compareTo(element);
             if (compare > 0)
                 l = m;
             else if (compare <0)
                 r = m;
             else
                 break;


         }
         return resp;


     }
}
