package spellchecker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StemmingNaziefNew extends OlahDokumen {
	

	    private String kata;
	    private String kembalikanKata;
	    private String akarKata;
	    private ArrayList<String> listKamus;
	    private String readDokumen;
	    public boolean cekPrefiksSuffiks;
	    private String bersikan = "";
	    //private String hapusInfleksional;

	    public StemmingNaziefNew() throws FileNotFoundException, IOException {
	        listKamus = new ArrayList<String>();
	        bacaKamus();
	    }

	    /**
	     * Membaca isi kamus dan memasukkan kata-kata di dalam kamus ke dalam sebuah list
	     * @throws FileNotFoundException
	     * @throws IOException 
	     */
	    public void bacaKamus() throws FileNotFoundException, IOException {
	        readDokumen = readDokumenTeks("kbbi.txt");
	        StringTokenizer strKamus = new StringTokenizer(readDokumen);
	        while (strKamus.hasMoreTokens()) {
	            listKamus.add(strKamus.nextToken());
	        }
	    }  

	    /**
	     * Kata diinputkan
	     * @param kata 
	     */
	    public void setKata(String kata) {
	        this.kata = kata;
	        this.akarKata = kata;
	        bersikan = "";
	    }

	    /**
	     * Kata yang diinputkan dicek, apakah sesuai di dalam kamus, jika kata yang diinputkan
	     * sesuai dengan yang ada pada kamus maka kata tersebut merupakan akar kata (root word),
	     * @param kata
	     * @return 
	     */
	    public boolean cekKamus(String kata) {
	        if (listKamus.contains(kata)) {
	            return true;
	        } else {
	            return false;
	        }
	    }
	    
	       
	    private final String HapusAkhiran(String kata) {
	    	Pattern ptn = Pattern.compile("([A-Za-z]+)([klt]ah|pun|ku|mu|nya)$", Pattern.CASE_INSENSITIVE);
	    	Matcher mtcher = ptn.matcher(kata);

	        if (mtcher.matches()) {
	            String key = mtcher.group(1);
	            return HapusAkhiranKepunyaan(key);
	        }
	        
	        return HapusAkhiranKepunyaan(kata);
	    }
	    
	    private final String HapusAkhiranKepunyaan(String kata) {
	    	Pattern ptn = Pattern.compile("([A-Za-z]+)(nya|[km]u)$", Pattern.CASE_INSENSITIVE);
	    	Matcher mtcher = ptn.matcher(kata);
	    	
	    	if (mtcher.matches()) {
	            String key = mtcher.group(1);
	            return key;
	        }
	    	
	    	return kata; 
	    }
	    
	    private final String HapusAkhiranIAnKan(String kata) {
	        String kataasal = kata;
	        
	        if (Pattern.compile("(kan)$", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	            String kata1 = kata.replaceFirst("(kan)$", ""); 
	            if (cekKamus(kata1)) {
	                return kata1;
	            }
	            
	        }
	        
	        if (Pattern.compile("(i|an)$", Pattern.CASE_INSENSITIVE).matcher(kata).find()){
	            String kata2 = kata.replaceFirst("(i|an)$", ""); 
	            if (cekKamus(kata2)) {
	                return kata2;
	            }
	            
	        }
	        
	        return kataasal;
	    }
	    
	    
	    private final String hapus_derivation_prefix(String kata) {
	        String kataasal = kata;
	        
	        //Region cek di ke se 
	        
	        if (Pattern.compile("^(di|[ks]e)", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	            String kata1 = kata.replaceFirst("^(di|[ks]e)", "");
	            if (cekKamus(kata1)) {
	                return kata1;
	            }
	            
	            String kata2 = HapusAkhiranIAnKan(kata1);
	            if (cekKamus(kata2)) {
	                return kata2;
	            }
	            
	        }
	        
	        //EndRegion
	        
	        //Region cek te me be pe 
	        if (Pattern.compile("^([tmbp]e)", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	        	
	        	//Region Awalan be 
	        	
	            if (Pattern.matches("^(be)", kataasal)) {
	                if (Pattern.matches("^(ber)[aiueo]", kataasal)) {
	                    String kata1 = kata.replaceAll("^(ber)", "");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    String kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                    kata1 = kata.replaceAll("^(ber)", "r");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                }
	                
	                if (Pattern.compile("^(ber)[^aiueor][A-Za-z](?!er)", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                    String kata1 = kata.replaceAll("^(ber)", "");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    String kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                }
	               
	                
	                if (Pattern.compile("^(ber)[^aiueor][A-Za-z]er[aiueo]", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                    String kata1 = kata.replaceAll("^(ber)", "");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    String kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                }
	                
	                if (Pattern.compile("^(belajar)", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                    String kata1 = kata.replaceAll("^(bel)", "");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    String kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                }
	                
	                if (Pattern.compile("^(ber)[^aiueor]er[^aiueo]", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                    String kata1 = kata.replaceAll("^(ber)", "");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    String kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                }
	                
	            }        
	            //EndRegion
	            
	            //Region Awalan te
	            
	            if (Pattern.compile("^(te)", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                if (Pattern.compile("^(terr)", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                    return kata;
	                }
	                
	                if (Pattern.compile("^(ter)[aioue]", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                    String kata1 = kata.replaceAll("^(ter)", "");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    String kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                    kata1 = kata.replaceAll("^(ter)", "r");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                }
	                
	            }
	            
	            if (Pattern.compile("^(ter)[^aiueor]er[aiueo]", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                String kata1 = kata.replaceAll("^(ter)", "");
	                if (cekKamus(kata1)) {
	                    return kata1;
	                }
	                
	                String kata2 = HapusAkhiranIAnKan(kata1);
	                if (cekKamus(kata2)) {
	                    return kata2;
	                }
	                
	            }
	            
	            if (Pattern.compile("^(ter)[^aiueor](?!er)", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                String kata1 = kata.replaceAll("^(ter)", "");
	                if (cekKamus(kata1)) {
	                    return kata1;
	                }
	                
	                String kata2 = HapusAkhiranIAnKan(kata1);
	                if (cekKamus(kata2)) {
	                    return kata2;
	                }
	                
	            }
	            
	            if (Pattern.compile("^(te)[^aiueor]er[aiueo]", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                String kata1 = kata.replaceAll("^(te)", "");
	                if (cekKamus(kata1)) {
	                    return kata1;
	                }
	                
	                String kata2 = HapusAkhiranIAnKan(kata1);
	                if (cekKamus(kata2)) {
	                    return kata2;
	                }
	                
	            }
	            
	            if (Pattern.compile("^(ter)[^aiueor]er[^aiueo]", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                String kata1 = kata.replaceAll("^(ter)", "");
	                if (cekKamus(kata1)) {
	                    return kata1;
	                }
	                
	                String kata2 = HapusAkhiranIAnKan(kata1);
	                if (cekKamus(kata2)) {
	                    return kata2;
	                }
	                
	            }           
	            //EndRegion
	            
	            //Region Awalan me
	            
	            if (Pattern.compile("^(me)", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                if (Pattern.compile("^(me)[lrwyv][aiueo]", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                    String kata1 = kata.replaceAll("^(me)", "");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    String kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                }
	                
	                if (Pattern.compile("^(mem)[bfvp]", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                    String kata1 = kata.replaceAll("^(mem)", "");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    String kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                }
	                
	                if (Pattern.compile("^(mem)((r[aiueo])|[aiueo])", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                    String kata1 = kata.replaceAll("^(mem)", "m");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    String kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                    kata1 = kata.replaceAll("^(mem)", "p");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                }
	                
	                if (Pattern.compile("^(men)[cdjszt]", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                    String kata1 = kata.replaceAll("^(men)", "");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    String kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                }
	                
	                if (Pattern.compile("^(men)[aiueo]", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                    String kata1 = kata.replaceAll("^(men)", "n");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    String kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                    kata1 = kata.replaceAll("^(men)", "t");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                }
	                
	                if (Pattern.compile("^(meng)[ghqk]", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                    String kata1 = kata.replaceAll("^(meng)", "");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    String kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                }
	                
	                if (Pattern.compile("^(meng)[aiueo]", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                    String kata1 = kata.replaceAll("^(meng)", "");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    String kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                    kata1 = kata.replaceAll("^(meng)", "k");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                    kata1 = kata.replaceAll("^(menge)", "");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                }
	                
	                if (Pattern.compile("^(meny)[aiueo]", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                    String kata1 = kata.replaceAll("^(meny)", "s");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    String kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                    kata1 = kata.replaceAll("^(me)", "");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                }
	                
	            }
	            
	            //EndRegion
	            
	            //Region Awalan pe
	            
	            if (Pattern.compile("^(pe)", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                if (Pattern.compile("^(pe)[wy]", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                    String kata1 = kata.replaceAll("^(pe)", "");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    String kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                }
	                
	                if (Pattern.compile("^(per)[aiueo]", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                    String kata1 = kata.replaceAll("^(per)", "");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    String kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                    kata1 = kata.replaceAll("^(per)", "r");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                }
	                
	                if (Pattern.compile("^(per)[^aiueor][A-Za-z](?!er)", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                    String kata1 = kata.replaceAll("^(per)", "");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    String kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                }
	                
	                if (Pattern.compile("^(per)[^aiueor][A-Za-z](er)[aiueo]", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                    String kata1 = kata.replaceAll("^(per)", "");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    String kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                }
	                
	                if (Pattern.compile("^(pembelajaran)", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                    String kata1 = kata.replaceAll("^(pembelajaran)", "ajar");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    String kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                }
	                
	                if (Pattern.compile("^(pem)[bfv]", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                    String kata1 = kata.replaceAll("^(pem)", "");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    String kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                }
	                
	                if (Pattern.compile("^(pem)(r[aiueo]|[aiueo])", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                    String kata1 = kata.replaceAll("^(pem)", "m");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    String kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                    kata1 = kata.replaceAll("^(pem)", "p");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                }
	                
	                if (Pattern.compile("^(pen)[cdjzt]", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                    String kata1 = kata.replaceAll("^(pen)", "");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    String kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                }
	                
	                if (Pattern.compile("^(pen)[aiueo]", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                    String kata1 = kata.replaceAll("^(pen)", "n");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    String kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                    kata1 = kata.replaceAll("^(pen)", "t");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                }
	                
	                if (Pattern.compile("^(peng)[^aiueo]", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                    String kata1 = kata.replaceAll("^(peng)", "");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    String kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                }
	                
	                if (Pattern.compile("^(peng)[aiueo]", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                    String kata1 = kata.replaceAll("^(peng)", "");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    String kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                    kata1 = kata.replaceAll("^(peng)", "k");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                    kata1 = kata.replaceAll("^(penge)", "");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                }
	                
	                if (Pattern.compile("^(peny)[aiueo]", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                    String kata1 = kata.replaceAll("^(peny)", "s");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    String kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                    kata1 = kata.replaceAll("^(pe)", "");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                }
	                
	                if (Pattern.compile("^(pel)[aiueo]", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                    String kata1 = kata.replaceAll("^(pel)", "l");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    String kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                }
	                
	                if (Pattern.compile("^(pelajar)", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                    String kata1 = kata.replaceAll("^(pel)", "");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    String kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                }
	                
	                if (Pattern.compile("^(pe)[^rwylmn]er[aiueo]", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                    String kata1 = kata.replaceAll("^(pe)", "");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    String kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                }
	                
	                if (Pattern.compile("^(pe)[^rwylmn](?!er)", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                    String kata1 = kata.replaceAll("^(pe)", "");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    String kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                }
	                
	                if (Pattern.compile("^(pe)[^aiueo]er[^aiueo]", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	                    String kata1 = kata.replaceAll("^(pe)", "");
	                    if (cekKamus(kata1)) {
	                        return kata1;
	                    }
	                    
	                    String kata2 = HapusAkhiranIAnKan(kata1);
	                    if (cekKamus(kata2)) {
	                        return kata2;
	                    }
	                    
	                }
	                
	            }
	            
	        }
	        
	        //EndRegion
	      
	        //EndRegion
	        
		    //Region memper dkk 
		     
	        if (Pattern.compile("^(memper)", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	            String kata1 = kata.replaceAll("^(memper)", "");
	            if (cekKamus(kata1)) {
	                return kata1;
	            }
	            
	            String kata2 = HapusAkhiranIAnKan(kata1);
	            if (cekKamus(kata2)) {
	                return kata2;
	            }
	            
	            kata1 = kata.replaceAll("^(memper)", "r");
	            if (cekKamus(kata1)) {
	                return kata1;
	            }
	            
	            kata2 = HapusAkhiranIAnKan(kata1);
	            if (cekKamus(kata2)) {
	                return kata2;
	            }
	            
	        }
	        
	        if (Pattern.compile("^(mempel)", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	            String kata1 = kata.replaceAll("^(mempel)", "");
	            if (cekKamus(kata1)) {
	                return kata1;
	            }
	            
	            String kata2 = HapusAkhiranIAnKan(kata1);
	            if (cekKamus(kata2)) {
	                return kata2;
	            }
	            
	            kata1 = kata.replaceAll("^(mempel)", "l");
	            if (cekKamus(kata1)) {
	                return kata1;
	            }
	            
	            kata2 = HapusAkhiranIAnKan(kata1);
	            if (cekKamus(kata2)) {
	                return kata2;
	            }
	            
	        }
	        
	        if (Pattern.compile("^(menter)", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	            String kata1 = kata.replaceAll("^(menter)", "");
	            if (cekKamus(kata1)) {
	                return kata1;
	            }
	            
	            String kata2 = HapusAkhiranIAnKan(kata1);
	            if (cekKamus(kata2)) {
	                return kata2;
	            }
	            
	            kata1 = kata.replaceAll("^(menter)", "r");
	            if (cekKamus(kata1)) {
	                return kata1;
	            }
	            
	            kata2 = HapusAkhiranIAnKan(kata1);
	            if (cekKamus(kata2)) {
	                return kata2;
	            }
	            
	        }
	        
	        if (Pattern.compile("^(member)", Pattern.CASE_INSENSITIVE).matcher(kata).find()) {
	            String kata1 = kata.replaceAll("^(member)", "");
	            if (cekKamus(kata1)) {
	                return kata1;
	            }
	            
	            String kata2 = HapusAkhiranIAnKan(kata1);
	            if (cekKamus(kata2)) {
	                return kata2;
	            }
	            
	            kata1 = kata.replaceAll("^(member)", "r");
	            if (cekKamus(kata1)) {
	                return kata1;
	            }
	            
	            kata2 = HapusAkhiranIAnKan(kata1);
	            if (cekKamus(kata2)) {
	                return kata2;
	            }
	            
	        }
	        
	        //EndRegion
	        
		    //Region diper 

	        if(Pattern.compile("^(diper)", Pattern.CASE_INSENSITIVE).matcher(kata).find()){
	        	String kata1 = kata.replaceAll("^(diper)", "");
		        if (cekKamus(kata1)) {
		            return kata1;
		        }
		        
		        String kata2 = HapusAkhiranIAnKan(kata1);
		        if (cekKamus(kata2)) {
		            return kata2;
		        }
		        
		        kata1 = kata.replaceAll("^(diper)", "r");
		        if (cekKamus(kata1)) {
		            return kata1;
		        }
		        
		        kata2 = HapusAkhiranIAnKan(kata1);
		        if (cekKamus(kata2)) {
		            return kata2;
		        }
	        }
	        
	        //EndRegion
	         
		    //Region diter  

	        if (Pattern.compile("^(diter)", Pattern.CASE_INSENSITIVE).matcher(kata).find()){
	        	String kata1 = kata.replaceAll("^(diter)", "");
		        if (cekKamus(kata1)) {
		            return kata1;
		        }
		        
		        String kata2 = HapusAkhiranIAnKan(kata1);
		        if (cekKamus(kata2)) {
		            return kata2;
		        }
		        
		        kata1 = kata.replaceAll("^(diter)", "r");
		        if (cekKamus(kata1)) {
		            return kata1;
		        }
		        
		        kata2 = HapusAkhiranIAnKan(kata1);
		        if (cekKamus(kata2)) {
		            return kata2;
		        }
	        }
	        
	        //EndRegion
	        	        
	        //Region dipel 
		     
	        if(Pattern.compile("^(dipel)", Pattern.CASE_INSENSITIVE).matcher(kata).find()){
	        	String kata1 = kata.replaceAll("^(dipel)", "");
		        if (cekKamus(kata1)) {
		            return kata1;
		        }
		        
		        String kata2 = HapusAkhiranIAnKan(kata1);
		        if (cekKamus(kata2)) {
		            return kata2;
		        }
		        
		        kata1 = kata.replaceAll("^(dipel)", "l");
		        if (cekKamus(kata1)) {
		            return kata1;
		        }
		        
		        kata2 = HapusAkhiranIAnKan(kata1);
		        if (cekKamus(kata2)) {
		            return kata2;
		        }
	        }
	        
	        //EndRegion 
	        
		    //Region diber 
	      
	        if(Pattern.compile("^(diber)", Pattern.CASE_INSENSITIVE).matcher(kata).find()){
	        	String kata1 = kata.replaceAll("^(diber)", "");
		        if (cekKamus(kata1)) {
		            return kata1;
		        }
		        
		        String kata2 = HapusAkhiranIAnKan(kata1);
		        if (cekKamus(kata2)) {
		            return kata2;
		        }
		        
		        kata1 = kata.replaceAll("^(diber)", "r");
		        if (cekKamus(kata1)) {
		            return kata1;
		        }
		        
		        kata2 = HapusAkhiranIAnKan(kata1);
		        if (cekKamus(kata2)) {
		            return kata2;
		        }
	        }
	        
	        //EndRegion 
	        
		    //Region keber 
	        
	        if(Pattern.compile("^(keber)", Pattern.CASE_INSENSITIVE).matcher(kata).find()){
	        	String kata1 = kata.replaceAll("^(keber)", "");
		        if (cekKamus(kata1)) {
		            return kata1;
		        }
		        
		        String kata2 = HapusAkhiranIAnKan(kata1);
		        if (cekKamus(kata2)) {
		            return kata2;
		        }
		        
		        kata1 = kata.replaceAll("^(keber)", "r");
		        if (cekKamus(kata1)) {
		            return kata1;
		        }
		        
		        kata2 = HapusAkhiranIAnKan(kata1);
		        if (cekKamus(kata2)) {
		            return kata2;
		        }
	        }
	        
	        //EndRegion 
	        	        
		    //Region keter 
	        
	        if(Pattern.compile("^(keter)", Pattern.CASE_INSENSITIVE).matcher(kata).find()){
	        	String kata1 = kata.replaceAll("^(keter)", "");
		        if (cekKamus(kata1)) {
		            return kata1;
		        }
		        
		        String kata2 = HapusAkhiranIAnKan(kata1);
		        if (cekKamus(kata2)) {
		            return kata2;
		        }
		        
		        kata1 = kata.replaceAll("^(keter)", "r");
		        if (cekKamus(kata1)) {
		            return kata1;
		        }
		        
		        kata2 = HapusAkhiranIAnKan(kata1);
		        if (cekKamus(kata2)) {
		            return kata2;
		        }
	        }
	        
	        //EndRegion
	        
	        //Region berke 
        
	        if(Pattern.compile("^(berke)", Pattern.CASE_INSENSITIVE).matcher(kata).find()){
	        	String kata1 = kata.replaceAll("^(berke)", "");
		        if (cekKamus(kata1)) {
		            return kata1;
		        }
		        
		        String kata2 = HapusAkhiranIAnKan(kata1);
		        if (cekKamus(kata2)) {
		            return kata2;
		        }
	        }
	        
	        //EndRegion

	        //Region cek di ke se te be me
	        if ((Pattern.compile("^(di|[kstbmp]e)", Pattern.CASE_INSENSITIVE).matcher(kata).find() == true)) {
	            return kataasal;
	        }
	        
	        return kataasal;
	        //EndRegion
	    }
	    
	    public final String Stemming(String kata) {
	        if (cekKamus(kata)) {
	            return kata;
	        }else{
	        	String kata1 = HapusAkhiran(kata);
	        	kata1 = HapusAkhiranIAnKan(kata1);
	        	kata1 = hapus_derivation_prefix(kata1);
	        	return kata1;
	        	/*if (cekKamus(kata1)) {
		            return kata1;
	        	}else{
	        		
	    	        return kata2;
	        	}*/
	        }
	        
	        
	        
	        
	    }

}
