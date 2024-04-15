import java.io.IOException;
import java.io.RandomAccessFile;

public class ArquivoJava {
    private int numeroRegistroTot = 1024;
    private String nomearquivo;
    private RandomAccessFile arquivo;
    private int comp,mov;

    public ArquivoJava(String nomearquivo)
    {
        try
        {
            arquivo = new RandomAccessFile(nomearquivo, "rw");
        } catch (IOException e)
        { }
    }


    public RandomAccessFile getFile()
    {
        return arquivo;
    }
    public void truncate(long pos) //desloca eof
    {
        try
        {
            arquivo.setLength(pos * Registro.length());
        } catch (IOException exc)
        { }
    }

    public void gravarString(String frase){
        try {
            arquivo.writeBytes(frase);
        } catch (IOException e) {
            System.out.println("ERRO: " + e);
        }
    }

    //semelhante ao feof() da linguagem C
    //verifica se o ponteiro esta no <EOF> do arquivo
    public boolean eof()
    {
        boolean retorno = false;
        try
        {
            if (arquivo.getFilePointer() == arquivo.length())
                retorno = true;
        } catch (IOException e)
        { }
        return (retorno);
    }

    public int filesize() {
        try {
            return (int) arquivo.length() / Registro.length();
        } catch (IOException e){}
        return 0;
    }

    //insere um Registro no final do arquivo, passado por par�metro
    public void inserirRegNoFinal(Registro reg)
    {
        seekArq(filesize());//ultimo byte
        reg.gravaNoArq(arquivo);
    }


    public void arquivoOrdenado ()
    {
        Registro reg = new Registro();
        truncate(0);
        for(int i=1; i <= numeroRegistroTot; i++)   
        {
            reg.setCodigo(i);
            reg.gravaNoArq(arquivo);
        }
    }

    public void arquivoInvertido ()
    {
        Registro reg = new Registro();
        truncate(0);
        for(int i=numeroRegistroTot; i >0 ; i--)   
        {
            reg.setCodigo(i);
            reg.gravaNoArq(arquivo);
        }
    }

    public void initComp() {
        comp = 0;
    }

    public void initMov() {
        mov = 0;
    }

    public int getComp() {
        return this.comp;
    }

    public int getMov() {
        return this.mov;
    }

    public Registro clone(Registro reg){
        return new Registro(reg.getCodigo());
    }

    public int getNumRegistros() {
        return numeroRegistroTot;
    }

    public void exibe(){
        seekArq(0);
        Registro reg = new Registro();
        for(int i=0;i<filesize();i++){
            reg.leDoArq(getFile());
            System.out.print(reg.getCodigo()+" ");
        }
        System.out.println("");
    } 

    public void copiaArquivo(ArquivoJava arquivoOrigem)
    {
        Registro reg = new Registro();
        seekArq(0);
        arquivoOrigem.seekArq(0);
        while (!arquivoOrigem.eof()) {
            reg.leDoArq(arquivoOrigem.getFile());
            reg.gravaNoArq(arquivo);
        }
    }

    public void geraArquivoRandomico() 
    {
        Registro registro = new Registro();
        truncate(0);
        for(int i=1; i <= numeroRegistroTot; i++) 
        {
            registro.setCodigo((int) (Math.random() * (numeroRegistroTot * 2) + 1));
            registro.gravaNoArq(arquivo);
        }
    }


    public void seekArq(int pos)
    {
        try
        {
            arquivo.seek(pos * Registro.length());
        } catch (IOException e)
        { }
    }


    //metodos
    public int buscaExaustiva(int codigo) {
        Registro reg = new Registro();
        int pos=0;
        seekArq(0);
        if(filesize() > 0){
            reg.leDoArq(arquivo);
            while(!eof() && codigo != reg.getCodigo()) {
                reg.leDoArq(arquivo);
                pos++;
            }
            if(codigo == reg.getCodigo()) {
                return pos;
            }
        }
        return -1;
    }

    //## BUBBLE SORT
    public void bubbleSort() {
        int tl=filesize();
        Registro regA = new Registro(), regB = new Registro();
        boolean flag = true;
        while(tl > 1 && flag) {
            flag = false;
            for (int i=0; i < tl-1; i++) {
                seekArq(i);
                regA.leDoArq(arquivo);
                regB.leDoArq(arquivo);

                if(regA.getCodigo() > regB.getCodigo()) {
                    seekArq(i);
                    regB.gravaNoArq(arquivo);
                    regA.gravaNoArq(arquivo);
                    flag = true;
                    mov+=2;
                }
                comp++;
            }
            tl--;
        }
    }
    //----------------------------------

    //## INSERÇÃO DIRETA
    public void insercaoDireta ()
    {
        Registro reg = new Registro(), regAux = new Registro();
        int TL = filesize();
        int pos;

        for(int i=1;i<TL;i++)
        {
            seekArq(i-1);
            reg.leDoArq(arquivo);
            regAux.leDoArq(arquivo);
            pos = i;

            while(pos > 0 && regAux.getCodigo() < reg.getCodigo())
            {
                comp++;
                seekArq(pos--);
                mov++;
                reg.gravaNoArq(arquivo);
                if(pos>0)
                {
                    seekArq(pos-1);
                    reg.leDoArq(arquivo);
                }
            }

            if(pos!=i)
            {
                seekArq(pos);
                regAux.gravaNoArq(arquivo);
                mov++;
            }
        }
    }
    //---------------------------------------------------------

    //## SELEÇÃO DIRETA
    public void selecaoDireta()
    {
        Registro pi = new Registro(), pj = new Registro();
        int pos,menor;
        int TL = filesize();
        

        for(int i=0;i<TL-1;i++)
        {
            seekArq(i);
            pi.leDoArq(arquivo);
            menor = pi.getCodigo();
            pos = i;
            for(int j=i+1;j<TL;j++)
            {
                pj.leDoArq(arquivo);
                if(pj.getCodigo() < menor)
                {
                    menor = pj.getCodigo();
                    pos = j;
                }
                comp++;
            }

            // le os dados
            seekArq(i);
            pi.leDoArq(arquivo);
            seekArq(pos);
            pj.leDoArq(arquivo);

            // grava os dados
            seekArq(i);
            pj.gravaNoArq(arquivo);
            seekArq(pos);
            pi.gravaNoArq(arquivo);
            mov+=2;
        }
    }
    //--------------------------------------------------

    //## INSERÇÃO BINÁRIA
    private int buscaBinaria (int num,int TL)
    {
        int ini = 0, fim = TL, meio = TL/2;
        Registro reg = new Registro();
        seekArq(meio);
        reg.leDoArq(arquivo);

        while(ini < fim && reg.getCodigo() != num)
        {
            if(num > reg.getCodigo())
                ini = meio+1;
            else
                fim = meio-1;

            meio = (ini+fim)/2;
            seekArq(meio);
            reg.leDoArq(arquivo);
            comp+=2;
        }

        if(num>reg.getCodigo())
            return meio+1;
        return meio;
    }

    public void insercaoBinaria ()
    {
        int TL=filesize(), pos;
        Registro reg = new Registro(), regAux = new Registro();

        for(int i=1;i<TL;i++)
        {
            seekArq(i);
            reg.leDoArq(arquivo);
            pos = buscaBinaria(reg.getCodigo(), i);

            for(int j=i;j>pos;j--)
            {
                seekArq(j-1);
                regAux.leDoArq(arquivo);
                regAux.gravaNoArq(arquivo);
                mov++;
            }

            if(pos<i)
            {
                seekArq(pos);
                reg.gravaNoArq(arquivo);
                mov++;
            }
        }
    }
    //--------------------------------------------------------------

    //## SHAKE SORT
    public void shakeSort ()
    {
        int inicio = 0, fim = filesize()-1;
        boolean flag = true;
        Registro reg = new Registro(), regAux = new Registro();

        while(inicio < fim && flag)
        {
            flag = false;
            for(int i=inicio;i<fim;i++)
            {
                seekArq(i);
                reg.leDoArq(arquivo);
                regAux.leDoArq(arquivo);
                if(reg.getCodigo()> regAux.getCodigo())
                {
                    flag = true;
                    seekArq(i);
                    regAux.gravaNoArq(arquivo);
                    reg.gravaNoArq(arquivo);
                    mov+=2;
                }
                comp++;
            }
            fim--;
            if(flag)
            {
                flag = false;
                for(int i=fim;i>inicio;i--)
                {
                    seekArq(i-1);
                    regAux.leDoArq(arquivo);
                    reg.leDoArq(arquivo);
                    if(reg.getCodigo()<regAux.getCodigo())
                    {
                        flag = true;
                        seekArq(i-1);
                        reg.gravaNoArq(arquivo);
                        regAux.gravaNoArq(arquivo);
                        mov+=2;
                    }
                    comp++;
                }
                inicio++;
            }
        }
    }
    //---------------------------------------------------

    //## HEAP SORT
    public void heapSort()
    {
        int paiPos, TL = filesize(),posE,pos;
        Registro pai = new Registro(), fe = new Registro(), fd = new Registro(), maiorF = new Registro();

        while (TL > 1)
        {
            paiPos = TL/2-1;
            seekArq(paiPos);
            pai.leDoArq(arquivo);
            while(paiPos >=0)
            {
                posE = 2*paiPos+1;
                seekArq(posE);
                fe.leDoArq(arquivo);

                maiorF = fe;
                pos = posE;
                if(pos+1<TL) // verificar se filho existe
                {
                    fd.leDoArq(arquivo);
                    if(fd != null && fd.getCodigo()>fe.getCodigo())
                    {
                        pos++;
                        maiorF =  fd;
                    }
                    comp++;
                }
                
                if(maiorF.getCodigo() > pai.getCodigo())
                {
                    seekArq(pos);
                    pai.gravaNoArq(arquivo);
                    seekArq(paiPos);
                    maiorF.gravaNoArq(arquivo);
                    mov+=2;
                }
                comp++;
                paiPos--;
                seekArq(paiPos);
                pai.leDoArq(arquivo);
            }

            seekArq(0);
            fe.leDoArq(arquivo);
            seekArq(TL-1);
            fd.leDoArq(arquivo);

            seekArq(0);
            fd.gravaNoArq(arquivo);
            seekArq(TL-1);
            fe.gravaNoArq(arquivo);
            mov+=2;
            TL--;
        }
    }
    //------------------------------------------------

    //## SHELL SORT
    public void shellSort()
    {
        int i, j , dist =1;
        int TL = filesize();
        Registro aux = new Registro(), reg = new Registro();

        while (dist < TL) 
            dist = dist*3+1;
        dist = dist/3;

        while (dist > 0) 
        {
            for(i=0;i<TL;i++)
            {
                seekArq(i);
                aux.leDoArq(arquivo);
                j=i;
                seekArq(j-dist);
                reg.leDoArq(arquivo);

                while(j-dist >=0 && aux.getCodigo()<reg.getCodigo())
                {
                    seekArq(j);
                    reg.gravaNoArq(arquivo);
                    j = j-dist;
                    if(j -dist > 0)
                    {
                        seekArq(j-dist);
                        reg.leDoArq(arquivo);
                    }
                    mov+=2;
                }
                comp++;
                seekArq(j);
                aux.gravaNoArq(arquivo);
            }
            dist = dist/3;
        }
    }
    //--------------------------------------------------


    //## QUICK SORT SEM PIVO
    private void quicksp(int inicio, int fim)
    {
        int i = inicio, j = fim;
        Registro reg = new Registro(), regAux = new Registro();

        while (i<j) 
        {
            seekArq(i);
            reg.leDoArq(arquivo);
            seekArq(j);
            regAux.leDoArq(arquivo);

            while(i<j && reg.getCodigo()<= regAux.getCodigo())
            {
                comp++;
                seekArq(++i);
                reg.leDoArq(arquivo);
            }
            seekArq(i);
            regAux.gravaNoArq(arquivo);
            seekArq(j);
            reg.gravaNoArq(arquivo);

            while(i<j && reg.getCodigo() <= regAux.getCodigo())
            {
                mov+=2;
                seekArq(--j);
                regAux.leDoArq(arquivo);            
            }
            seekArq(i);
            regAux.gravaNoArq(arquivo);
            seekArq(j);
            reg.gravaNoArq(arquivo);
        }
        if(inicio < i-1)
            quicksp(inicio, i-1);
        if(j+1<fim)
            quicksp(j+1, fim);
    }

    public void quickSortSemPivo()
    {
        quicksp(0,filesize()-1);
    }
    //--------------------------------------------------------------


    //##  QUICK SORT COM PIVO
    private void quickcp(int inicio,int fim)
    {
        int i = inicio, j= fim, pivo;
        pivo = (inicio+fim)/2;
        Registro regI = new Registro(), regJ = new Registro();
        Registro regPivo = new Registro();

        seekArq(pivo);
        regPivo.leDoArq(arquivo);
        while(i<j)
        {
            seekArq(i);
            regI.leDoArq(arquivo);
            while(regI.getCodigo() < regPivo.getCodigo())
            {
                comp++;
                seekArq(++i);
                regI.leDoArq(arquivo);
            }
                
            seekArq(j);
            regJ.leDoArq(arquivo);
            while(regJ.getCodigo() > regPivo.getCodigo())
            {
                comp++;
                seekArq(--j);
                regJ.leDoArq(arquivo);
            }

            if(i<=j)
            {
                seekArq(i);
                regJ.gravaNoArq(arquivo);
                seekArq(j);
                regI.gravaNoArq(arquivo);
                i++;
                j--;
                mov+=2;
            }
        }
        if(inicio<j)
            quickcp(inicio, j);
        if(i<fim)
            quickcp(i, fim);

    }
    public void quickSortComPivo()
    {
        quickcp(0,filesize()-1);
    }
    //---------------------------------------

    //## MERGE SORT 1° IMPLEMENTAÇÃO 
    public void MergeSort1()
    {
        int TL = filesize(), seq =1;
        ArquivoJava parte1 = new ArquivoJava("parte1.dat");
        ArquivoJava parte2 = new ArquivoJava("parte2.dat");

        while (seq<TL) 
        {
            parte1.truncate(0); // resetar os arquivos
            parte2.truncate(0);
            particaoMerge1(parte1, parte2);
            fusaoMerge1(parte1, parte2, seq);
            seq*=2;
        }

    }

    private void particaoMerge1(ArquivoJava arq1, ArquivoJava arq2)
    {
        Registro reg = new Registro();
        int meio = filesize()/2;
        for(int i=0;i<meio;i++)
        {
            seekArq(i);
            reg.leDoArq(arquivo);
            reg.gravaNoArq(arq1.getFile());
            
            seekArq(meio+1);
            reg.leDoArq(arquivo);
            reg.gravaNoArq(arq2.getFile());
            mov+=2;
        }
    }

    private void fusaoMerge1 (ArquivoJava arq1, ArquivoJava arq2, int seq)
    {
        int k=0,i=0,j=0,auxSeq = seq;
        Registro regI = new Registro(), regJ = new Registro();
        truncate(0); // começo do arquivo, escrever sequencial

        while(k<filesize())
        {
            while(i<seq && j<seq)
            {
                arq1.seekArq(i); regI.leDoArq(arq1.arquivo);
                arq2.seekArq(j); regJ.leDoArq(arq2.arquivo);
                if(regI.getCodigo() < regJ.getCodigo())
                {
                    regI.gravaNoArq(arquivo);
                    i++; k++;
                }
                else
                {
                    regJ.gravaNoArq(arquivo);
                    j++; k++;
                }
                mov++;
                comp++;
            }

            while(i<seq)
            {
                arq1.seekArq(i); regI.leDoArq(arq1.arquivo);
                regI.gravaNoArq(arquivo);
                i++; k++;
                mov++;
            }

            while(j<seq)
            {
                arq2.seekArq(j); regJ.leDoArq(arq2.arquivo);
                regJ.gravaNoArq(arquivo);
                j++; k++;
                mov++;
            }
            seq += auxSeq;
        }
    }
    //--------------------------------------------------------------


    //## MERGE SORT 2° IMPLEMENTAÇÃO INTERATIVO
    public void MergeSort2 ()
    {
        ArquivoJava aux = new ArquivoJava("aux.dat");
        Pilha p1 = new Pilha(), p2 = new Pilha();
        int ini, fim,meio, ini2, fim2;

        
        p1.push(0);
        p1.push(filesize()-1);
        while(!p1.isEmpty())
        {  
            fim = p1.pop();
            ini = p1.pop();
            if(ini<fim)
            {
                meio = (ini+fim)/2;
                p1.push(ini);
                p1.push(meio);
                p2.push(ini);
                p2.push(meio);

                p1.push(meio+1);
                p1.push(fim);
                p2.push(meio+1);
                p2.push(fim);
            }
        }

        while(!p2.isEmpty())
        {
            fim2 = p2.pop();
            ini2 = p2.pop();
            fim = p2.pop();
            ini = p2.pop();
            fusaoMerge2(aux, ini, fim, ini2, fim2);
        }
    }

    private void fusaoMerge2(ArquivoJava arq, int ini1, int fim1,int ini2,int fim2)
    {
        int i = ini1, j= ini2;
        Registro reg1 = new Registro(), reg2 = new Registro();
        arq.truncate(0);

        while(i<=fim1 && j<=fim2)
        {
            seekArq(i);
            reg1.leDoArq(arquivo);
            seekArq(j);
            reg2.leDoArq(arquivo);

            if(reg1.getCodigo() < reg2.getCodigo())
            {
                reg1.gravaNoArq(arq.arquivo);
                i++;
            }
            else
            {
                reg2.gravaNoArq(arq.arquivo);
                j++;
            }
            comp++;
            mov++;
        }

        while(i<=fim1)
        {
            seekArq(i); reg1.leDoArq(arquivo);
            reg1.gravaNoArq(arq.arquivo);
            i++;
            mov++;
        }

        while(j<=fim2)
        {
            seekArq(j); reg2.leDoArq(arquivo);
            reg2.gravaNoArq(arq.arquivo);
            j++;
            mov++;
        }

        seekArq(ini1);
        arq.seekArq(0);
        while(!arq.eof())
        {
            reg1.leDoArq(arq.arquivo);
            reg1.gravaNoArq(arquivo);
            mov++;
        }
    } 
    //----------------------------------------------------

    //## COMB SORT
    public void CombSort()
    {
        int TL = filesize();
        int intervalo = (int)(TL/1.3), indice = 0;
        Registro reg = new Registro(), regAux = new Registro();

        if(TL> 1)
        {
            while(intervalo > 0 && indice != filesize()-1)
            {
                indice = 0;
                while((indice+intervalo)<TL)
                {
                    seekArq(indice);
                    reg.leDoArq(arquivo);
                    seekArq(indice+intervalo);
                    regAux.leDoArq(arquivo);
                    if(reg.getCodigo() > regAux.getCodigo())
                    {
                        seekArq(indice);
                        regAux.gravaNoArq(arquivo);
                        seekArq(indice+intervalo);
                        reg.gravaNoArq(arquivo);
                        mov+=2;
                    }
                    comp++;
                    indice++;
                }
                intervalo = (int)(intervalo/1.3);
            }
        }
    }
    //--------------------------------------------------

    //## GNOME SORT
    public void gnomeSort ()
    {
        int i=1, guardaPosi;
        int TL = filesize();
        Registro reg = new Registro(), regAux = new Registro();
        while(i<TL)
        {
            seekArq(i);
            reg.leDoArq(arquivo);
            if(i>=1)
            {
                seekArq(i-1);
                regAux.leDoArq(arquivo);
            }
            if(reg != null && reg.getCodigo() >= regAux.getCodigo())
                i++;
            else
            {
                guardaPosi = i;
                seekArq(i);
                reg.leDoArq(arquivo);
                if(i>0)
                {
                    seekArq(i-1);
                    regAux.leDoArq(arquivo);
                }
                while(i>0 && reg.getCodigo() < regAux.getCodigo())
                {
                    seekArq(i);
                    regAux.gravaNoArq(arquivo);
                    seekArq(i-1);
                    reg.gravaNoArq(arquivo);
                    i--;
                    seekArq(i);
                    reg.leDoArq(arquivo);
                    if(i>0)
                    {
                        seekArq(i-1);
                        regAux.leDoArq(arquivo);
                    }
                    mov+=2;
                }
                i = guardaPosi;
            }
            comp++;
        }
    }
    //-----------------------------------------

    //## RADIX SORT
    private int BuscaMaior(int TL){
        int maior=-1;
        Registro reg = new Registro();
        for(int i=0;i<TL;i++){
            seekArq(i); reg.leDoArq(arquivo);
            if(reg.getCodigo()>maior)
                maior = reg.getCodigo();
            comp++;
        }
        return maior;
    }

    private void somaFreq(ArquivoJava freArq, int TL)
    {
        int soma;
        Registro reg = new Registro();
        for(int i=1;i<TL;i++)
        {   
            soma=0;
            freArq.seekArq(i-1); reg.leDoArq(freArq.getFile());
            soma += reg.getCodigo(); reg.leDoArq(freArq.getFile());
            soma += reg.getCodigo(); reg.setCodigo(soma);
            freArq.seekArq(i); reg.gravaNoArq(freArq.getFile());
            mov++;
        }
    }

    private void freqRadix (ArquivoJava freq,int div)
    {
        int posFreq;
        Registro reg = new Registro(), aux = new Registro();
        freq.truncate(0);
        reg.setCodigo(0);
        for(int i=0;i<10;i++){
            reg.gravaNoArq(freq.getFile());
            mov++;
        }
        for(int i=0;i<filesize();i++)
        {
            seekArq(i); reg.leDoArq(arquivo);
            posFreq = (reg.getCodigo()/div)%10;
            freq.seekArq(posFreq); reg.leDoArq(freq.getFile());
            reg.setCodigo(reg.getCodigo()+1);
            freq.seekArq(posFreq); reg.gravaNoArq(freq.getFile());
            mov++;
        }
    }

    private void freqSaida (ArquivoJava freq, ArquivoJava saida, int div)
    {
        int nAux, posFreq, posSaida;
        Registro reg = new Registro();
        for(int i=filesize()-1;i>=0;i--)
        {
            seekArq(i); reg.leDoArq(arquivo);
            nAux = reg.getCodigo(); 
            posFreq = (nAux/div)%10;
            freq.seekArq(posFreq); reg.leDoArq(freq.getFile());
            posSaida = reg.getCodigo()-1; 
            reg.setCodigo(reg.getCodigo()-1); 
            freq.seekArq(posFreq); reg.gravaNoArq(freq.getFile());
            reg.setCodigo(nAux);
            saida.seekArq(posSaida); reg.gravaNoArq(saida.getFile()); 
            mov+=2;
        }
    }


    public void radixSort ()
    {
        ArquivoJava freArq = new ArquivoJava("frequencia.dat"), 
        saidaArq = new ArquivoJava("saida.dat");
        int maior, TL = filesize(), div=1;
        Registro reg = new Registro();
        maior = BuscaMaior(TL);
        reg.setCodigo(0);
        saidaArq.truncate(0);
        while(maior>0)
        {
            freqRadix(freArq, div);
            somaFreq(freArq, 10);
            freqSaida(freArq, saidaArq, div);
            div*=10;
            maior/=div;
            for(int i=0;i<TL;i++)
            {
                saidaArq.seekArq(i); reg.leDoArq(saidaArq.getFile());
                seekArq(i); reg.gravaNoArq(arquivo);
                mov++;
            }
        }
    }
    //----------------------------------------------------------------

    //## BUCKET SORT
    public void bucketSort() // vou fazer utilizando 4 baldes
    {
        int maior, TL = filesize(), qtdBalde = 4, inter, pos[] = {0};
        ArquivoJava balde1 = new ArquivoJava("balde1.dat"),
        balde2 = new ArquivoJava("balde2.dat"),
        balde3 = new ArquivoJava("balde3.dat"),
        balde4 = new ArquivoJava("balde4.dat");
        Registro reg = new Registro(), regB = new Registro();

        //reiniciar todos os arquivos
        balde1.truncate(0);
        balde2.truncate(0);
        balde3.truncate(0);
        balde4.truncate(0);

        balde1.seekArq(0); reg.gravaNoArq(balde1.getFile());
        balde2.seekArq(0); reg.gravaNoArq(balde2.getFile());
        balde3.seekArq(0); reg.gravaNoArq(balde3.getFile());
        balde4.seekArq(0); reg.gravaNoArq(balde4.getFile());
        mov+=4;

        if(TL>0){
            maior = BuscaMaior(TL);
            inter = maior/qtdBalde;

            for(int i=0;i<TL;i++){
                seekArq(i); reg.leDoArq(arquivo);

                if(reg.getCodigo()<=inter){
                    balde1.seekArq(0); regB.leDoArq(balde1.getFile());
                    balde1.seekArq(regB.getCodigo()+1); reg.gravaNoArq(balde1.getFile());
                    regB.setCodigo(regB.getCodigo()+1);
                    balde1.seekArq(0); regB.gravaNoArq(balde1.getFile());
                    comp++;
                }
                else if(reg.getCodigo()<=2*inter){
                    balde2.seekArq(0); regB.leDoArq(balde2.getFile());
                    balde2.seekArq(regB.getCodigo()+1); reg.gravaNoArq(balde2.getFile());
                    regB.setCodigo(regB.getCodigo()+1);
                    balde2.seekArq(0); regB.gravaNoArq(balde2.getFile());
                    comp+=2;
                }
                else if(reg.getCodigo()<=3*inter){
                    balde3.seekArq(0); regB.leDoArq(balde3.getFile());
                    balde3.seekArq(regB.getCodigo()+1); reg.gravaNoArq(balde3.getFile());
                    regB.setCodigo(regB.getCodigo()+1);
                    balde3.seekArq(0); regB.gravaNoArq(balde3.getFile());
                    comp+=3;
                }
                else{
                    balde4.seekArq(0); regB.leDoArq(balde4.getFile());
                    balde4.seekArq(regB.getCodigo()+1); reg.gravaNoArq(balde4.getFile());
                    regB.setCodigo(regB.getCodigo()+1);
                    balde4.seekArq(0); regB.gravaNoArq(balde4.getFile());
                    comp+=3;
                }
                mov++;
            }
            balde1.seekArq(0); regB.leDoArq(balde1.getFile());
            balde1.insercaoDireta();

            balde2.seekArq(0); regB.leDoArq(balde2.getFile());
            balde2.insercaoDireta();

            balde3.seekArq(0); regB.leDoArq(balde3.getFile());
            balde3.insercaoDireta();

            balde4.seekArq(0); regB.leDoArq(balde4.getFile());
            balde4.insercaoDireta();

            truncate(0);
            gravarBucket(balde1, pos);
            gravarBucket(balde2, pos);
            gravarBucket(balde3, pos);
            gravarBucket(balde4, pos);
        }
    }

    public void gravarBucket(ArquivoJava balde, int pos[]){
        Registro reg = new Registro();
        seekArq(pos[0]);
        balde.seekArq(1);
        while (!balde.eof()) {
            reg.leDoArq(balde.getFile());
            reg.gravaNoArq(arquivo);
            pos[0]++;
            mov++;
        }
    }
    //-------------------------------------------------------


    //## COUTING SORT
    private void montaFreqCount(ArquivoJava freArq, int maior){
        Registro reg = new Registro();
        int posFreq;
        reg.setCodigo(0);
        freArq.truncate(0);
        for(int i = 0; i<maior; i++)
        {
            reg.gravaNoArq(freArq.getFile());
            mov++;
        }

        for(int i=0;i<filesize();i++)
        {
            seekArq(i); reg.leDoArq(arquivo);
            posFreq = reg.getCodigo()-1;
            freArq.seekArq(posFreq); reg.leDoArq(freArq.getFile());
            reg.setCodigo(reg.getCodigo()+1); //incrementa a freArq registrada
            freArq.seekArq(posFreq); reg.gravaNoArq(freArq.getFile());
            mov++;
        }

    }

    private void montaSaidaCount(ArquivoJava freqArq, ArquivoJava saidaArq){
        int nAux, posFreq, posSaida;
        Registro reg = new Registro();
        for(int i=filesize()-1;i>=0;i--)
        {
            seekArq(i); reg.leDoArq(arquivo);
            nAux = reg.getCodigo(); 
            posFreq = nAux-1;
            freqArq.seekArq(posFreq); reg.leDoArq(freqArq.getFile());
            posSaida = reg.getCodigo()-1; 
            reg.setCodigo(reg.getCodigo()-1); 
            freqArq.seekArq(posFreq); reg.gravaNoArq(freqArq.getFile());
            reg.setCodigo(nAux);
            saidaArq.seekArq(posSaida); reg.gravaNoArq(saidaArq.getFile());
            mov+=2;
        }
    }

    public void coutingSort ()
    {
        int maior, TL = filesize();
        ArquivoJava frequencia = new ArquivoJava("frequencia.dat"), 
        saida = new ArquivoJava("saida.dat");
        Registro reg = new Registro();
        maior = BuscaMaior(TL);

        montaFreqCount(frequencia, maior);
        
        somaFreq(frequencia, maior);

        montaSaidaCount(frequencia, saida);

        for(int i=0;i<TL;i++)
        {
            saida.seekArq(i); reg.leDoArq(saida.getFile());
            seekArq(i); reg.gravaNoArq(arquivo);
            mov++;
        }
    }
    //-----------------------------------------------------------


    //## TIM SORT
    public void timInsert(int ini, int fim)
    {
        Registro aux = new Registro(), reg1 = new Registro(), reg2 = new Registro();
        int TL, i=ini+1, pos;
        if(fim<0)
            TL = filesize();
        else
            TL = fim;
        
        while(i<TL)
        {
            seekArq(i); aux.leDoArq(arquivo);
            pos = buscaBinaria(aux.getCodigo(), i);
            for(int j=i; j>pos; j--){
                seekArq(j-1); reg1.leDoArq(arquivo);
                reg2.leDoArq(arquivo);
                seekArq(j-1); reg2.gravaNoArq(arquivo);
                reg1.gravaNoArq(arquivo);
                mov+=2;
            }
            i++;
        }
    }
    public void timSort()
    {
        int div=32, TL=filesize(), dir, meio;
        ArquivoJava aux = new ArquivoJava("tim.dat");
        aux.truncate(TL);
        for(int i=0;i<TL;i+=div){
            if(i+div<TL)
                timInsert(i, i+div);
            else
                timInsert(i, TL);
        }

        for(int tam=div;tam<TL;tam*=2){
            for(int esq=0;esq<TL;esq+=2*tam){
                if(esq+2*tam<TL)
                    dir = esq + 2*tam -1;
                else
                    dir = TL - 1;
                meio = (esq+dir)/2;
                fusaoMerge2(aux,esq, meio, meio+1, dir);
            }
        }
    }
}
