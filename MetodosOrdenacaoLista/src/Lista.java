
public class Lista {
    
    private No inicio;
    private No fim;
    
    
    
    public Lista() {
        
    }
    
    public void inicializa ()
    {
        inicio = fim = null;
    }
    
    public No getInicio() {
        return inicio;
    }
    
    public void setInicio(No inicio) {
        this.inicio = inicio;
    }
    
    public No getFim() {
        return fim;
    }

    public void setFim(No fim) {
        this.fim = fim;
    }
    
    public void inserirInicio (int info)
    {
        No nova = new No(null, inicio, info);
        if(inicio == null)
            inicio=fim=nova;
        else
        {
            inicio.setAnt(nova);
            inicio = nova;
        }
    }

    public void inserirFim (int info)
    {
        No nova = new No(fim, null, info);
        if(inicio == null)
            inicio = fim = nova;
        else
        {
            fim.setProx(nova);
            fim = nova;
        }
    }

    public void inserirInfos (Lista list)
    {
        list.inicializa();
        list.inserirInicio(10);
        list.inserirInicio(9);
        list.inserirFim(2);
        list.inserirInicio(16);
        list.inserirFim(5);
        list.inserirFim(7);
        list.inserirFim(3);
        list.inserirFim(3);
        list.inserirFim(4);
        list.inserirFim(1);
        list.inserirFim(6);
        list.inserirFim(8);
        list.inserirFim(12);
        list.inserirFim(19);
        list.inserirFim(15);
        list.inserirFim(20);
    }


    public void exibir()
    {
        No aux = inicio;
        while (aux!=null) {
            System.out.print(aux.getInfo()+" ");
            aux = aux.getProx();
        }
        System.out.println();
    }

    public No buscaExaustiva (int info)
    {
        No aux = inicio;
        while (aux != null && aux.getInfo()!= info) {  
            aux = aux.getProx();
        }
        return aux;
    }
   
    public void remover (int info)
    {
        No aux = buscaExaustiva(info);

        if(aux!=null)
        {
            if(inicio==fim)
                inicio = fim = null;
            else
            {
                if(aux == inicio)
                {
                    aux.getProx().setAnt(null);
                    inicio = aux.getProx();
                }
                else
                {
                    if(aux == fim)
                    {
                        aux.getAnt().setProx(null);
                        fim = aux.getAnt();
                    }
                    else
                    {
                        aux.getAnt().setProx(aux.getProx());
                        aux.getProx().setAnt(aux.getAnt());
                    }
                }
            }
        }
    }

    public No getNo (int pos)
    {
        No aux = inicio;
        int cont = 0;
        while(aux!=null && cont <pos)
        {
            cont++;
            aux = aux.getProx();
        }

        return aux;
    }

    public int posicaoNo (No no)
    {
        No aux = inicio;
        int cont = 0;
        while(aux!=null && aux!=no)
        {
            cont++;
            aux = aux.getProx();
        }
        return cont;
    }
    //---------------------------------------------------------------------


    //## INSERÇÃO DIRETA

    public void insercaoDireta ()
    {
        No aux, pos;
        int num;

        if(inicio!=null)
            aux = inicio.getProx();
        else
            aux = null;

        while(aux != null)
        {
            num = aux.getInfo();
            pos = aux;
            while(pos.getAnt() != null && num< pos.getAnt().getInfo())
            {
                pos.setInfo(pos.getAnt().getInfo());
                pos = pos.getAnt();
            }

            pos.setInfo(num);
            aux = aux.getProx();
        }
    }
    //----------------------------------------------------------------------------------
    

    //## SELEÇÃO DIRETA
    public void selecaoDireta ()
    {
        No pontmenor, pi = inicio, pj;
        int aux;

        while (pi.getProx()!= null) {
            pontmenor = pi;
            pj = pi.getProx();
            while (pj != null) {
                if(pj.getInfo() < pontmenor.getInfo())
                    pontmenor = pj;
                pj = pj.getProx();
            }

            aux = pi.getInfo();
            pi.setInfo(pontmenor.getInfo());
            pontmenor.setInfo(aux);
            pi = pi.getProx();
        }
    }
    //----------------------------------------------------------------------------------


    //## INSERÇÃO BINARIA
    public No buscaBinaria(int chave, No TL) {
       int inic = posicaoNo(inicio);
       int fim = posicaoNo(TL);
       int meio = fim/2;
       No aux = getNo(meio);

        while (inic < fim && chave != aux.getInfo())
        {
            if(chave > aux.getInfo())
                inic = meio+1;
            else    
                fim = meio-1;

            meio = (fim+inic)/2;
            aux = getNo(meio);
        }
        if(chave > aux.getInfo())
            return aux.getProx();
        return aux; 
    }
    
    public void insercaoBinaria() {
        No pi = inicio.getProx();
        while (pi != null) 
        {
            int num = pi.getInfo();
            No pos = buscaBinaria(num, pi);
    
            No pj = pi;
            while (pj.getAnt() != null && pj != pos) {
                pj.setInfo(pj.getAnt().getInfo());
                pj = pj.getAnt();
            }
    
            if (pos != null) {
                pos.setInfo(num);
            }
    
            pi = pi.getProx();
        }
    }
    //----------------------------------------------------------------------------------


    //## BUBBLE SORT
    public void BubbleSort()
    {
        No pi,pTL = fim;
        boolean flag = true;
        int aux;

        while(pTL != inicio && flag)
        {
            pi = inicio;
            flag = false;
            while(pi!=pTL)
            {
                if(pi.getInfo()>pi.getProx().getInfo())
                {
                    flag = true;
                    aux = pi.getInfo();
                    pi.setInfo(pi.getProx().getInfo());
                    pi.getProx().setInfo(aux);
                }
                pi = pi.getProx();
            }
            pTL = pTL.getAnt();
        }
    }
    //----------------------------------------------------------------------------------


    //## SHAKE SORT
    public void ShakeSort ()
    {
        No pini =inicio, pfim =fim, pi,pf;
        int aux;
        boolean flag =true;

        while(pini!=pfim && flag)
        {
            flag = false;
            pi = pini;
            pf = pfim;

            while(pi!=pf)
            {
                if(pi.getInfo() > pi.getProx().getInfo())
                {
                    flag = true;
                    aux = pi.getInfo();
                    pi.setInfo(pi.getProx().getInfo());
                    pi.getProx().setInfo(aux);
                }
                pi = pi.getProx();
            }
            
            pf = pfim = pfim.getAnt();

            if (flag) 
            {
                flag = false;
                while(pf != inicio)
                {
                    if(pf.getInfo() < pf.getAnt().getInfo())
                    {
                        flag = true;
                        aux = pf.getInfo();
                        pf.setInfo(pf.getAnt().getInfo());
                        pf.getAnt().setInfo(aux);
                    }
                    pf = pf.getAnt();
                }
            }
            pini = pini.getProx();
        }
    }
    //----------------------------------------------------------------------------------


    //## HEAP SORT
    public void HeapSort()
    {
        int TL2,pai,FE,FD,aux,maiorF;

        TL2 = posicaoNo(fim)+1; //  posição nó retorna o int de onde esta o ultimo campo, e não tamanho, por isso o +1
        while(TL2 >1)
        {
            pai = TL2/2-1;
            while (pai>=0) 
            {
                FE = pai*2+1;
                FD = FE+1;
                maiorF = FE;
                if(FD < TL2 && getNo(FD).getInfo() > getNo(FE).getInfo())
                    maiorF = FD;

                if(getNo(maiorF).getInfo() > getNo(pai).getInfo())
                {
                    aux = getNo(maiorF).getInfo();
                    getNo(maiorF).setInfo(getNo(pai).getInfo());
                    getNo(pai).setInfo(aux);
                }
                pai--;
            }
            aux = getNo(TL2-1).getInfo();
            getNo(TL2-1).setInfo(getNo(0).getInfo());
            getNo(0).setInfo(aux);
            TL2--;
        }
    }
    //----------------------------------------------------------------------------------


    //## COUTING SORT
    public void CoutingSort ()
    {
        int maiorV=0,vetAux[],vetNovo[],TL = posicaoNo(fim)+1;
        No aux = inicio;

        // pegar maior elemento para definir tamanho do vetor auxiliar
        while(aux!=null) 
        {
            if(aux.getInfo()>maiorV)
                maiorV = aux.getInfo();
            aux = aux.getProx();
        }
        vetAux = new int[maiorV];

        // colocar a frequencia/contagem no vetor auxiliar
        for (aux = inicio; aux != null; aux = aux.getProx())
            vetAux[aux.getInfo() - 1] += 1;

        // quantidade de valores menores ou iguais a um determinado elemento
        for (int i = 1; i < maiorV; i++)
            vetAux[i] += vetAux[i - 1];


        //vetor novo para colocar os elementos ordenados
        vetNovo = new int[TL];
        for (aux = fim; aux != null; aux = aux.getAnt()) {
            vetNovo[vetAux[aux.getInfo() - 1] - 1] = aux.getInfo();
            vetAux[aux.getInfo() - 1] -= 1;
        }


        //substituir os valores na lista
        aux = inicio;
        for (int i = 0; i < TL; i++) {
            aux.setInfo(vetNovo[i]);
            aux = aux.getProx();
        }
    }
    //----------------------------------------------------------------------------------

    
    //##RADIX SORT
    public void radixSort() 
    {
        Lista vetorDigito[] = new Lista[10]; //0 a 9
        int div = 1, rest, cont = 0,result;
        No ini = inicio, auxLista;

        //inicializar vetor de listas
        for(int i=0;i<10;i++)
            vetorDigito[i] =  new Lista();

        
        while(ini != null) // repetição para pegar o digito menos significativo
        {
            result = ini.getInfo()/div;
            if(result == 0)
                cont++;
            rest = result%10; // pega resto para achar posicao
            vetorDigito[rest].inserirFim(ini.getInfo());
            ini = ini.getProx();
        }
        div *=10;

        while(cont != posicaoNo(fim)+1)
        {
            cont = 0;
            ini = inicio;

            for(int j=0;j<10;j++) // jogar os valores do vetor auxiliar na lista original
            {
                auxLista = vetorDigito[j].getInicio();
                while(auxLista!=null) // percorre toda a lista de cada digito
                {
                    ini.setInfo(auxLista.getInfo());
                    ini = ini.getProx();
                    auxLista = auxLista.getProx();
                }
            }

            for(int i=0;i<10;i++)        // resetar a lista para inserir novos valores
                vetorDigito[i] =  new Lista();

            ini = inicio;
            while(ini != null) // repetição para pegar o digito menos significativo de acordo com o divisor --> div*=10
            {
                result = ini.getInfo()/div;
                if(result == 0)
                    cont++;
                rest = result%10; // pega resto para achar posicao
                vetorDigito[rest].inserirFim(ini.getInfo());
                ini = ini.getProx();
            }
            div *=10;
        }
    }
    
    //----------------------------------------------------------------------------------
    
    //## SHELL SORT
    public void shellSort()
    {
        int i,j, dist =1,aux;
        int TL = posicaoNo(fim)+1;

        while (dist < TL)
            dist = dist*3+1;
        dist = dist/3;

        while(dist > 0)
        {
            for(i=dist;i<TL;i++)
            {
                //inserção direta com a dist
                aux = getNo(i).getInfo();
                j=i;
                while(j-dist >= 0 && aux < getNo(j-dist).getInfo())
                {   
                    getNo(j).setInfo(getNo(j-dist).getInfo());
                    j= j-dist;
                }
                getNo(j).setInfo(aux);
            }
            dist = dist/3;
        }
    }
    //------------------------------------------------------------------

    //## BUCKET SORT
    public void bucketSort() 
    {
        int TL = posicaoNo(fim) + 1; // Obtém o tamanho da lista
        int maiorValor = 0; // Inicializa os valores máximo e mínimo
        int indexBalde;
        Lista[] VetorLista; // Vetor de listas
        Lista novaLista = new Lista(); // Lista para armazenar os elementos ordenados
        No lista = inicio, auxNo;
    
        // Calcula o maior valor da lista
        while (lista != null) {
            if (lista.getInfo() > maiorValor)
                maiorValor = lista.getInfo();
            lista = lista.getProx();
        }
    
        // Calcula o número de baldes
        int numeroBaldes = 1;                            
        while(numeroBaldes<TL)
            numeroBaldes = numeroBaldes*4+1;   // essa condição pode mudar de acordo com a quantidade de balde que deseja
        numeroBaldes = numeroBaldes/4;
        VetorLista = new Lista[numeroBaldes];
    
        // Inicializa todas as listas
        for (int i = 0; i < numeroBaldes; i++) {
            VetorLista[i] = new Lista();
            VetorLista[i].inicializa();
        }
    
        // Insere os valores nos baldes
        lista = inicio; // Reinicia o ponteiro para o início da lista
        while (lista != null) {
            indexBalde = (int) (((double)lista.getInfo()/maiorValor)*(numeroBaldes-1));
            VetorLista[indexBalde].inserirFim(lista.getInfo());
            lista = lista.getProx();
        }
    
        // Ordena cada balde individualmente
        for (int i = 0; i < numeroBaldes; i++) {
            VetorLista[i].insercaoDireta();
        }
    
        // Junta tudo em uma lista única
        for (int i = 0; i < numeroBaldes; i++) {
            auxNo = VetorLista[i].getInicio();
            while (auxNo != null) {
                novaLista.inserirFim(auxNo.getInfo());
                auxNo = auxNo.getProx();
            }
        }
    
        // exibir os baldes com os respectivos numeros
        for (int i = 0; i < numeroBaldes; i++) {
            System.out.print("Balde " + i + ": ");
            No aux = VetorLista[i].getInicio(); // Obtém o nó inicial da lista do balde
            while (aux != null) {
                System.out.print(aux.getInfo() + " ");
                aux = aux.getProx();
            }
            System.out.println();
        }

        // Joga os valores para a lista principal
        No aux = inicio;
        No aux2 = novaLista.getInicio();
        while (aux2!=null) {
            aux.setInfo(aux2.getInfo());
            aux = aux.getProx();
            aux2 = aux2.getProx();
        }
    }   
    //----------------------------------------------------------

   //## GNOME SORT
   public void gnomeSort()
   {
        int i=1,aux,guardaPosi;
        int TL = posicaoNo(fim)+1;               // ver com o chico para guardar a posição ta certo ou pode otimizar
        while(i<TL)
        {
            if(getNo(i)!= null && i>=0 &&  getNo(i).getInfo() >= getNo(i-1).getInfo())    // verificar se o i>=0 é certo utilizar
                i++;
            else
            {
                guardaPosi = i;
                while(i > 0 && getNo(i).getInfo() < getNo(i-1).getInfo())
                {
                    aux = getNo(i).getInfo();
                    getNo(i).setInfo(getNo(i).getAnt().getInfo());
                    getNo(i).getAnt().setInfo(aux);
                    i--;
                }
                i = guardaPosi;
            }
        }
   }
   //----------------------------------------------------------------


   //## QUICK SORT SEM PIVÔ
   public void QuickSPivo ()
   {
        quickSp(0, posicaoNo(fim));
   }

   private void quickSp(int ini,int fim)
   {
        int i = ini, j = fim, aux;
        while(i<j)
        {
            while(i<j && getNo(i).getInfo() <= getNo(j).getInfo())
                i++;
            aux = getNo(i).getInfo();
            getNo(i).setInfo(getNo(j).getInfo());
            getNo(j).setInfo(aux);

            while(i<j && getNo(j).getInfo() >= getNo(i).getInfo())
                j--;
            aux = getNo(i).getInfo();
            getNo(i).setInfo(getNo(j).getInfo());
            getNo(j).setInfo(aux);
        }
        if(ini < i-1)
            quickSp(ini, i-1);
        if(j+1<fim)
            quickSp(j+1, fim);
   }
   //-----------------------------------------------------------------

   //## QUICK SORT SEM PIVO ITERATIVO
   public void quickSortSPivoIterativo ()
   {
    Pilha p = new Pilha();
    int ini = 0, fimm = posicaoNo(fim), aux;
    int i,j;

    //inserir dados na pilha
    p.push(ini);
    p.push(fimm);
    while(!p.isEmpty())
    {
        fimm = p.pop();       // desempilha e joga pra i
        ini = p.pop();
        i=ini;
        j = fimm;          

        while(i<j)
        {
            while(i<j && getNo(i).getInfo() <= getNo(j).getInfo())
                i++;
            aux = getNo(i).getInfo();
            getNo(i).setInfo(getNo(j).getInfo());
            getNo(j).setInfo(aux);

            while(i<j && getNo(j).getInfo() >= getNo(i).getInfo())
                j--;
            aux = getNo(i).getInfo();
            getNo(i).setInfo(getNo(j).getInfo());
            getNo(j).setInfo(aux);
        }

        if(ini < i-1)
        {
            p.push(ini);
            p.push(i-1);
        }
        if(j+1<fimm)
        {
            p.push(j+1);
            p.push(fimm);
        }
    }
   }
   //------------------------------------------------------------------


   //## QUICK SORT COM PIVÔ
   private void quickCp(int ini, int fim)
   {
    int i = ini, j = fim, aux, pivo;
    pivo = getNo((ini+fim)/2).getInfo();
    
    while (i < j) 
    {
        while (getNo(i).getInfo() < pivo)
            i++;
        while (getNo(j).getInfo() > pivo)
            j--;
    
        if (i <= j) 
        {
            aux = getNo(i).getInfo();
            getNo(i).setInfo(getNo(j).getInfo());
            getNo(j).setInfo(aux);
            i++;
            j--;
        }
    }
    
    if(ini < j)
        quickCp(ini, j);
    if(i < fim)
        quickCp(i, fim);
   }

   public void QuickCPivo()
   {
        quickCp(0,posicaoNo(fim));
   }
   //-----------------------------------------------------------

   //## QUICK SORT COM PIVÔ ITERATIVO
   public void quickSortCPivoIterativo ()
   {
    int ini = 0, fimm = posicaoNo(fim),aux,i,j,pivo;
    Pilha p = new Pilha();

    p.push(ini);
    p.push(fimm);
    
    while(!p.isEmpty())
    {
        j = fimm = p.pop();
        i = ini = p.pop();
        pivo = getNo((ini+fimm)/2).getInfo();

        while(getNo(i).getInfo() < pivo)
            i++;
        while(getNo(j).getInfo() > pivo)
            j--;
    
        if(i<=j)
        {
            aux = getNo(i).getInfo();
            getNo(i).setInfo(getNo(j).getInfo());
            getNo(j).setInfo(aux);
            i++;
            j--;
        }

        if(ini < j)
        {
            p.push(ini);
            p.push(j);
        }
        if(i < fimm)
        {
            p.push(i);
            p.push(fimm);
        }  
    }
   }
   //-----------------------------------------------------


   //## MERGE SORT - 1° implementação
   public void MergeSort1()
   {
        int TL = posicaoNo(fim)+1,seq=1;          
        Lista aux1 = new Lista(); aux1.inicializa();
        Lista aux2 = new Lista(); aux2.inicializa();

        while(seq < TL)
        {
            particaoMerge1(aux1,aux2);
            fusaoMerge1(aux1,aux2, seq);
            seq = seq*2;
        }
   }

   private void particaoMerge1 (Lista aux1, Lista aux2)
   {
        int meio = (posicaoNo(fim)+1)/2;
        aux1.inicializa(); aux2.inicializa();
        for(int i=0;i<meio;i++)
        {
            aux1.inserirFim(getNo(i).getInfo());
            aux2.inserirFim(getNo(i+meio).getInfo());
        }
   }

   private void fusaoMerge1(Lista aux1,Lista aux2,int seq)
   {
        int k=0,i=0,j=0,auxSeq = seq;

        while(k < posicaoNo(fim)+1)
        {
            while(i<seq && j<seq)
            {
                if(getNoMerge(i, aux1.getInicio()).getInfo() < getNoMerge(j, aux2.getInicio()).getInfo())
                    getNo(k++).setInfo(getNoMerge(i++, aux1.getInicio()).getInfo());
                else
                    getNo(k++).setInfo(getNoMerge(j++, aux2.getInicio()).getInfo());
            }
            
            while (i < seq) 
                getNo(k++).setInfo(getNoMerge(i++, aux1.getInicio()).getInfo());

            while (j < seq) 
                getNo(k++).setInfo(getNoMerge(j++, aux2.getInicio()).getInfo());

            seq += auxSeq;
        }
   }
   //-------------------------------------------------

   
   //## CombSort
   public void CombSort()
   {
        int TL = posicaoNo(fim)+1;
        int intervalo = (int)(TL/1.3),indice =0,aux;
        
        if(TL> 1)
        {
            while(intervalo > 0 && indice != posicaoNo(fim))
            {
                indice = 0;
                while((indice+intervalo) < TL)
                {
                    if(getNo(indice).getInfo() > getNo(indice+intervalo).getInfo())
                    {
                        aux = getNo(indice).getInfo();
                        getNo(indice).setInfo(getNo(indice+intervalo).getInfo());
                        getNo(indice+intervalo).setInfo(aux);
                    }
                    indice++;
                }
                intervalo = (int) (intervalo/1.3);
            }
        }    
   }
   //---------------------------------------------------------------------------
  

   //## MERGE SORT 2º IMPLEMENTACAO

   private void duplicaNova (Lista nova)
   {
        No aux = inicio;
        while(aux != null)
        {
            nova.inserirFim(aux.getInfo());
            aux = aux.getProx();
        }
   }

   public void MergeSort2 ()
   {
        Lista nova = new Lista();
        nova.inicializa();
        duplicaNova(nova);
        merge(nova.getInicio(), 0, posicaoNo(fim));
   }

   private void merge (No aux,int esq, int dir)
   {
        int meio;
        if(esq < dir)
        {
            meio = (esq+dir)/2;
            merge(aux, esq, meio);
            merge(aux, meio+1, dir);
            fusaoMerge2(aux, esq, meio, meio+1, dir);
        }
   }

   private void fusaoMerge2 (No aux,int ini1, int fim1, int ini2, int fim2)
   {
        int i = ini1, j = ini2, k=0;

        while(i<=fim1 && j <= fim2)
        {
            if(getNo(i).getInfo() < getNo(j).getInfo())
                getNoMerge(k++, aux).setInfo(getNo(i++).getInfo());
            else
                getNoMerge(k++, aux).setInfo(getNo(j++).getInfo());
        }

        while(i<=fim1)
            getNoMerge(k++, aux).setInfo(getNo(i++).getInfo());

        while (j<=fim2)
            getNoMerge(k++, aux).setInfo(getNo(j++).getInfo());

        for(int x=0;x<k;x++)
            getNo(x+ini1).setInfo(getNoMerge(x, aux).getInfo());
   }

   private No getNoMerge (int pos,No aux)
   {
        int cont = 0;
        No auxNo = aux;

        while(auxNo != null && cont<pos)
        {
            auxNo = auxNo.getProx();
            cont++;
        }

        return auxNo;
   }

   //---------------------------------------------------------------


   //## TIM SORT
   public void TimSort()  
   {
        int div = 32, TL = posicaoNo(fim)+1, dir, meio;

        for(int i=0;i<TL;i+=div)
        {
            if(i+div < TL)
                timInsercao(i, i+div);
            else
                timInsercao(i, TL);
        }

        for(int tam=div;tam<TL;tam*=2)
        {
            for(int esq=0;esq<TL;esq+=2*tam){
                if(esq+2*tam<TL)
                    dir = esq + 2*tam -1;
                else
                    dir = TL - 1;
                meio = (esq+dir)/2;
                fusaoMerge2(inicio, div, TL, dir, meio);
            }
        }
   }

   private void timInsercao (int ini, int fimm)
   {
        int TL, i = ini+1, pos,aux;

        if(fimm < 0)
            TL = posicaoNo(fim)+1;
        else
            TL = fimm;

        while (i<TL) 
        {
            pos = buscaExaustiva(getNo(i).getInfo()).getInfo();
            for(int j=i; j> pos; j--)
            {
                aux = getNo(j).getInfo();
                getNo(j).setInfo(getNo(j-1).getInfo());
                getNo(j-1).setInfo(aux);
            }
            i++;
        }
   }
   //-----------------------------------------------------
}
