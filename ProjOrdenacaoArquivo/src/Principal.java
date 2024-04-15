public class Principal {
    private ArquivoJava arqOrd, arqRev, arqRand, auxOrd, auxRev, auxRand;
    private ArquivoJava tabela;
    long tini, tfim, OrdTot, RevTot, RandTot, ordComp, revComp, randComp, ordMov, revMov, randMov, regTot;

    public Principal() {
        arqOrd = new ArquivoJava("arqOrd.dat");
        arqRev = new ArquivoJava("arqRev.dat");
        arqRand = new ArquivoJava("arqRand.dat");
        auxOrd = new ArquivoJava("auxOrd.dat");
        auxRev = new ArquivoJava("auxRev.dat");
        auxRand = new ArquivoJava("auxRand.dat");
    }

    private void gerarColunas() {
        String colunas = "Metodos Ordenacao  " +
                "|              Arquivo Ordenado                             " +
                "|                  Arquivo em Ordem Reversa                         " +
                "|                    Arquivo Randomico                            |" +
                "\r\n                   " +
                "| Comp.Prog. | Comp.Equa. |  Mov.Prog | Mov.Equa. |  Tempo  " +
                "|  Comp.Prog.  |  Comp.Equa.  |   Mov.Prog  |  Mov.Equa.  |  Tempo  " +
                "|  Comp.Prog.  |  Comp.Equa.  |   Mov.Prog |  Mov.Equa. |  Tempo  |";
        tabela.gravarString(colunas);
    }

    private void gravaLinhaTabela(String nome, long compO, double ordCompEqua, long movO, double ordMovEqua,
    long ttotalO,long compRev, double revCompEqua, long movRev, double revMovEqua,
    long ttotalRev,long compRand, double randCompEqua, long movRand, double randMovEqua, long ttotalRand) {
        String linha = (String.format("\r\n%18s |%10d  |%10f  |%9d  |%10f |%7s  |%12d  |%12f  |%11d  " +
                        "|%11f  |%7s  |%12d  |%12f  |%10d  |%10f  |%7s  |",
                nome, compO, ordCompEqua, movO, ordMovEqua, ttotalO + "",
                compRev, revCompEqua, movRev, revMovEqua, ttotalRev + "",
                compRand, randCompEqua, movRand, randMovEqua, ttotalRand + ""));
        tabela.gravarString(linha);
    }
    public void geraTabela() {

        tabela = new ArquivoJava("tabela.txt");
        double ordMovEqua, ordCompEqua, revMovEqua, revCompEqua, randMovEqua, randCompEqua;
        gerarColunas();
        arqOrd.arquivoOrdenado(); arqRev.arquivoInvertido();  arqRand.geraArquivoRandomico();
        regTot = arqOrd.getNumRegistros();
        //-----------------------
        //01 - Inserção direta
        //-----------------------

        //Arquivo ordenado
        auxOrd.copiaArquivo(arqOrd);
        auxOrd.initComp();
        auxOrd.initMov();
        tini = System.currentTimeMillis();
        auxOrd.insercaoDireta();
        tfim = System.currentTimeMillis();
        OrdTot = (tfim-tini)/1000; ordMov = auxOrd.getMov(); ordComp = auxOrd.getComp();
        ordCompEqua = regTot-1;
        ordMovEqua = 3*(regTot-1);
        
        //Arquivo reverso
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        tini = System.currentTimeMillis();
        auxRev.insercaoDireta();
        tfim = System.currentTimeMillis();
        RevTot = (tfim-tini)/1000; revMov = auxRev.getMov(); revComp = auxRev.getComp();
        revCompEqua = (regTot*regTot + regTot - 4)/4;
        revMovEqua = (regTot*regTot + 3*regTot - 4)/2;

        //Arquivo randômico
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        tini = System.currentTimeMillis();
        auxRand.insercaoDireta();
        tfim = System.currentTimeMillis();
        RandTot = (tfim-tini)/1000; randMov = auxRand.getMov(); randComp = auxRand.getComp();
        randCompEqua = (regTot * regTot + regTot - 2)/4;
        randMovEqua = (regTot*regTot + 9*regTot - 10)/4;

        //gravar na tabela
        gravaLinhaTabela("Inserção Direta", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot,
                                                 revComp, revCompEqua, revMov, revMovEqua, RevTot,
                                                 randComp, randCompEqua, randMov, randMovEqua, RandTot);
        //----------------------
        //2 - Inserção Binária
        //----------------------
        //Arquivo ordenado
        auxOrd.copiaArquivo(arqOrd);
        auxOrd.initComp();
        auxOrd.initMov();
        tini = System.currentTimeMillis();
        auxOrd.insercaoBinaria();
        tfim = System.currentTimeMillis();
        OrdTot = (tfim-tini)/1000; ordMov = auxOrd.getMov(); ordComp = auxOrd.getComp();
        ordCompEqua = regTot*(Math.log10(regTot)-Math.log(Math.E) + 0.5);
        ordMovEqua = 3*(regTot-1);
        
        //Arquivo reverso
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        tini = System.currentTimeMillis();
        auxRev.insercaoBinaria();
        tfim = System.currentTimeMillis();
        RevTot = (tfim-tini)/1000; revMov = auxRev.getMov(); revComp = auxRev.getComp();
        revCompEqua = regTot*(Math.log10(regTot)-Math.log(Math.E) + 0.5);
        revMovEqua = (regTot*regTot + 3*regTot - 4)/2;

        //Arquivo randômico
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        tini = System.currentTimeMillis();
        auxRand.insercaoBinaria();
        tfim = System.currentTimeMillis();
        RandTot = (tfim-tini)/1000; randMov = auxRand.getMov(); randComp = auxRand.getComp();
        randCompEqua = regTot*(Math.log10(regTot)-Math.log(Math.E) + 0.5);
        randMovEqua = (regTot*regTot + 9*regTot - 10)/4;

        //gravar na tabela
        gravaLinhaTabela("Inserção Binária", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot,
                                                revComp, revCompEqua, revMov, revMovEqua, RevTot,
                                                randComp, randCompEqua, randMov, randMovEqua, RandTot);
        //----------------------
        //3 - Seleção Direta
        //----------------------
        //Arquivo ordenado
        auxOrd.copiaArquivo(arqOrd);
        auxOrd.initComp();
        auxOrd.initMov();
        tini = System.currentTimeMillis();
        auxOrd.selecaoDireta();
        tfim = System.currentTimeMillis();
        OrdTot = (tfim-tini)/1000; ordMov = auxOrd.getMov(); ordComp = auxOrd.getComp();
        ordCompEqua = (regTot*regTot - regTot)/2;
        ordMovEqua = 3*(regTot-1);
        
        //Arquivo reverso
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        tini = System.currentTimeMillis();
        auxRev.selecaoDireta();
        tfim = System.currentTimeMillis();
        RevTot = (tfim-tini)/1000; revMov = auxRev.getMov(); revComp = auxRev.getComp();
        revCompEqua = (regTot*regTot - regTot)/2;
        revMovEqua = (regTot*regTot + 3*regTot - 4)/2;

        //Arquivo randômico
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        tini = System.currentTimeMillis();
        auxRand.selecaoDireta();
        tfim = System.currentTimeMillis();
        RandTot = (tfim-tini)/1000; randMov = auxRand.getMov(); randComp = auxRand.getComp();
        randCompEqua = (regTot*regTot - regTot)/2;
        randMovEqua = (regTot*(Math.log10(regTot)+0.577216));

        gravaLinhaTabela("Seleção", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot,
                                                revComp, revCompEqua, revMov, revMovEqua, RevTot,
                                                randComp, randCompEqua, randMov, randMovEqua, RandTot);
        //----------------------
        //4 - Bubble Sort
        //----------------------
        //Arquivo ordenado
        auxOrd.copiaArquivo(arqOrd);
        auxOrd.initComp();
        auxOrd.initMov();
        tini = System.currentTimeMillis();
        auxOrd.bubbleSort();
        tfim = System.currentTimeMillis();
        OrdTot = (tfim-tini)/1000; ordMov = auxOrd.getMov(); ordComp = auxOrd.getComp();
        ordCompEqua = (regTot*regTot - regTot)/2;
        ordMovEqua = 0;
        
        //Arquivo reverso
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        tini = System.currentTimeMillis();
        auxRev.bubbleSort();
        tfim = System.currentTimeMillis();
        RevTot = (tfim-tini)/1000; revMov = auxRev.getMov(); revComp = auxRev.getComp();
        revCompEqua = (regTot*regTot - regTot)/2;
        revMovEqua = 3*(regTot*regTot - regTot)/4;

        //Arquivo randômico
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        tini = System.currentTimeMillis();
        auxRand.bubbleSort();
        tfim = System.currentTimeMillis();
        RandTot = (tfim-tini)/1000; randMov = auxRand.getMov(); randComp = auxRand.getComp();
        randCompEqua = (regTot*regTot - regTot)/2;
        randMovEqua = 3*(regTot*regTot - regTot)/2;

        gravaLinhaTabela("Bubble", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot,
                                                revComp, revCompEqua, revMov, revMovEqua, RevTot,
                                                randComp, randCompEqua, randMov, randMovEqua, RandTot);
        
        //----------------------
        //5 - Shake Sort
        //----------------------
        //Arquivo ordenado
        auxOrd.copiaArquivo(arqOrd);
        auxOrd.initComp();
        auxOrd.initMov();
        tini = System.currentTimeMillis();
        auxOrd.shakeSort();
        tfim = System.currentTimeMillis();
        OrdTot = (tfim-tini)/1000; ordMov = auxOrd.getMov(); ordComp = auxOrd.getComp();
        ordCompEqua = (regTot*regTot - regTot)/2;
        ordMovEqua = 0;
        
        //Arquivo reverso
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        tini = System.currentTimeMillis();
        auxRev.shakeSort();
        tfim = System.currentTimeMillis();
        RevTot = (tfim-tini)/1000; revMov = auxRev.getMov(); revComp = auxRev.getComp();
        revCompEqua = (regTot*regTot - regTot)/2;
        revMovEqua = 3*(regTot*regTot - regTot)/4;

        //Arquivo randômico
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        tini = System.currentTimeMillis();
        auxRand.shakeSort();
        tfim = System.currentTimeMillis();
        RandTot = (tfim-tini)/1000; randMov = auxRand.getMov(); randComp = auxRand.getComp();
        randCompEqua = (regTot*regTot - regTot)/2;
        randMovEqua = 3*(regTot*regTot - regTot)/2;

        gravaLinhaTabela("Shake", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot,
                                                revComp, revCompEqua, revMov, revMovEqua, RevTot,
                                                randComp, randCompEqua, randMov, randMovEqua, RandTot);

        //----------------------
        //6 - Shell Sort
        //----------------------
        //Arquivo ordenado
        auxOrd.copiaArquivo(arqOrd);
        auxOrd.initComp();
        auxOrd.initMov();
        tini = System.currentTimeMillis();
        auxOrd.shellSort();
        tfim = System.currentTimeMillis();
        OrdTot = (tfim-tini)/1000; ordMov = auxOrd.getMov(); ordComp = auxOrd.getComp();
        ordCompEqua = 0;
        ordMovEqua = 0;
        
        //Arquivo reverso
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        tini = System.currentTimeMillis();
        auxRev.shellSort();
        tfim = System.currentTimeMillis();
        RevTot = (tfim-tini)/1000; revMov = auxRev.getMov(); revComp = auxRev.getComp();
        revCompEqua = 0;
        revMovEqua = 0;

        //Arquivo randômico
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        tini = System.currentTimeMillis();
        auxRand.shellSort();
        tfim = System.currentTimeMillis();
        RandTot = (tfim-tini)/1000; randMov = auxRand.getMov(); randComp = auxRand.getComp();
        randCompEqua = 0;
        randMovEqua = 0;

        gravaLinhaTabela("Shell", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot,
                                                revComp, revCompEqua, revMov, revMovEqua, RevTot,
                                                randComp, randCompEqua, randMov, randMovEqua, RandTot);
                                            
        //----------------------
        //7 - Heap Sort
        //----------------------
        //Arquivo ordenado
        auxOrd.copiaArquivo(arqOrd);
        auxOrd.initComp();
        auxOrd.initMov();
        tini = System.currentTimeMillis();
        auxOrd.heapSort();
        tfim = System.currentTimeMillis();
        OrdTot = (tfim-tini)/1000; ordMov = auxOrd.getMov(); ordComp = auxOrd.getComp();
        ordCompEqua = 0;
        ordMovEqua = 0;
        
        //Arquivo reverso
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        tini = System.currentTimeMillis();
        auxRev.heapSort();
        tfim = System.currentTimeMillis();
        RevTot = (tfim-tini)/1000; revMov = auxRev.getMov(); revComp = auxRev.getComp();
        revCompEqua = 0;
        revMovEqua = 0;

        //Arquivo randômico
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        tini = System.currentTimeMillis();
        auxRand.heapSort();
        tfim = System.currentTimeMillis();
        RandTot = (tfim-tini)/1000; randMov = auxRand.getMov(); randComp = auxRand.getComp();
        randCompEqua = 0;
        randMovEqua = 0;

        gravaLinhaTabela("Heap", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot,
                                                revComp, revCompEqua, revMov, revMovEqua, RevTot,
                                                randComp, randCompEqua, randMov, randMovEqua, RandTot);

        //----------------------
        //8 - Quick sem pivô
        //----------------------
        //Arquivo ordenado
        auxOrd.copiaArquivo(arqOrd);
        auxOrd.initComp();
        auxOrd.initMov();
        tini = System.currentTimeMillis();
        auxOrd.quickSortSemPivo();
        tfim = System.currentTimeMillis();
        OrdTot = (tfim-tini)/1000; ordMov = auxOrd.getMov(); ordComp = auxOrd.getComp();
        ordCompEqua = 0;
        ordMovEqua = 0;
        
        //Arquivo reverso
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        tini = System.currentTimeMillis();
        auxRev.quickSortSemPivo();
        tfim = System.currentTimeMillis();
        RevTot = (tfim-tini)/1000; revMov = auxRev.getMov(); revComp = auxRev.getComp();
        revCompEqua = 0;
        revMovEqua = 0;

        //Arquivo randômico
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        tini = System.currentTimeMillis();
        auxRand.quickSortSemPivo();
        tfim = System.currentTimeMillis();
        RandTot = (tfim-tini)/1000; randMov = auxRand.getMov(); randComp = auxRand.getComp();
        randCompEqua = 0;
        randMovEqua = 0;

        gravaLinhaTabela("Quick s/ pivô", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot,
                                                revComp, revCompEqua, revMov, revMovEqua, RevTot,
                                                randComp, randCompEqua, randMov, randMovEqua, RandTot);

        //----------------------
        //9 - Quick com pivô
        //----------------------
        //Arquivo ordenado
        auxOrd.copiaArquivo(arqOrd);
        auxOrd.initComp();
        auxOrd.initMov();
        tini = System.currentTimeMillis();
        auxOrd.quickSortComPivo();
        tfim = System.currentTimeMillis();
        OrdTot = (tfim-tini)/1000; ordMov = auxOrd.getMov(); ordComp = auxOrd.getComp();
        ordCompEqua = 0;
        ordMovEqua = 0;
        
        //Arquivo reverso
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        tini = System.currentTimeMillis();
        auxRev.quickSortComPivo();
        tfim = System.currentTimeMillis();
        RevTot = (tfim-tini)/1000; revMov = auxRev.getMov(); revComp = auxRev.getComp();
        revCompEqua = 0;
        revMovEqua = 0;

        //Arquivo randômico
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        tini = System.currentTimeMillis();
        auxRand.quickSortComPivo();
        tfim = System.currentTimeMillis();
        RandTot = (tfim-tini)/1000; randMov = auxRand.getMov(); randComp = auxRand.getComp();
        randCompEqua = 0;
        randMovEqua = 0;

        gravaLinhaTabela("Quick c/ pivô", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot,
                                                revComp, revCompEqua, revMov, revMovEqua, RevTot,
                                                randComp, randCompEqua, randMov, randMovEqua, RandTot);

        //----------------------
        //10 - Merge 1ª Implementação
        //----------------------
        //Arquivo ordenado
        auxOrd.copiaArquivo(arqOrd);
        auxOrd.initComp();
        auxOrd.initMov();
        tini = System.currentTimeMillis();
        auxOrd.MergeSort1();
        tfim = System.currentTimeMillis();
        OrdTot = (tfim-tini)/1000; ordMov = auxOrd.getMov(); ordComp = auxOrd.getComp();
        ordCompEqua = 0;
        ordMovEqua = 0;
        
        //Arquivo reverso
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        tini = System.currentTimeMillis();
        auxRev.MergeSort1();
        tfim = System.currentTimeMillis();
        RevTot = (tfim-tini)/1000; revMov = auxRev.getMov(); revComp = auxRev.getComp();
        revCompEqua = 0;
        revMovEqua = 0;

        //Arquivo randômico
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        tini = System.currentTimeMillis();
        auxRand.MergeSort1();
        tfim = System.currentTimeMillis();
        RandTot = (tfim-tini)/1000; randMov = auxRand.getMov(); randComp = auxRand.getComp();
        randCompEqua = 0;
        randMovEqua = 0;

        gravaLinhaTabela("Merge 1ª Implement", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot,
                                                revComp, revCompEqua, revMov, revMovEqua, RevTot,
                                                randComp, randCompEqua, randMov, randMovEqua, RandTot);

        //----------------------
        //11 - Merge 2ª Implementação
        //----------------------
        //Arquivo ordenado
        auxOrd.copiaArquivo(arqOrd);
        auxOrd.initComp();
        auxOrd.initMov();
        tini = System.currentTimeMillis();
        auxOrd.MergeSort2();
        tfim = System.currentTimeMillis();
        OrdTot = (tfim-tini)/1000; ordMov = auxOrd.getMov(); ordComp = auxOrd.getComp();
        ordCompEqua = 0;
        ordMovEqua = 0;
        
        //Arquivo reverso
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        tini = System.currentTimeMillis();
        auxRev.MergeSort2();
        tfim = System.currentTimeMillis();
        RevTot = (tfim-tini)/1000; revMov = auxRev.getMov(); revComp = auxRev.getComp();
        revCompEqua = 0;
        revMovEqua = 0;

        //Arquivo randômico
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        tini = System.currentTimeMillis();
        auxRand.MergeSort2();
        tfim = System.currentTimeMillis();
        RandTot = (tfim-tini)/1000; randMov = auxRand.getMov(); randComp = auxRand.getComp();
        randCompEqua = 0;
        randMovEqua = 0;

        gravaLinhaTabela("Merge 2ª Implement", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot,
                                                revComp, revCompEqua, revMov, revMovEqua, RevTot,
                                                randComp, randCompEqua, randMov, randMovEqua, RandTot);

        //----------------------
        //12 - Counting Sort
        //----------------------
        //Arquivo ordenado
        auxOrd.copiaArquivo(arqOrd);
        auxOrd.initComp();
        auxOrd.initMov();
        tini = System.currentTimeMillis();
        auxOrd.coutingSort();
        tfim = System.currentTimeMillis();
        OrdTot = (tfim-tini)/1000; ordMov = auxOrd.getMov(); ordComp = auxOrd.getComp();
        ordCompEqua = 0;
        ordMovEqua = 0;
        
        //Arquivo reverso
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        tini = System.currentTimeMillis();
        auxRev.coutingSort();
        tfim = System.currentTimeMillis();
        RevTot = (tfim-tini)/1000; revMov = auxRev.getMov(); revComp = auxRev.getComp();
        revCompEqua = 0;
        revMovEqua = 0;

        //Arquivo randômico
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        tini = System.currentTimeMillis();
        auxRand.coutingSort();
        tfim = System.currentTimeMillis();
        RandTot = (tfim-tini)/1000; randMov = auxRand.getMov(); randComp = auxRand.getComp();
        randCompEqua = 0;
        randMovEqua = 0;

        gravaLinhaTabela("Counting", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot,
                                                revComp, revCompEqua, revMov, revMovEqua, RevTot,
                                                randComp, randCompEqua, randMov, randMovEqua, RandTot);

        //----------------------
        //13 - Bucket Sort
        //----------------------
        //Arquivo ordenado
        auxOrd.copiaArquivo(arqOrd);
        auxOrd.initComp();
        auxOrd.initMov();
        tini = System.currentTimeMillis();
        auxOrd.bucketSort();
        tfim = System.currentTimeMillis();
        OrdTot = (tfim-tini)/1000; ordMov = auxOrd.getMov(); ordComp = auxOrd.getComp();
        ordCompEqua = 0;
        ordMovEqua = 0;
        
        //Arquivo reverso
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        tini = System.currentTimeMillis();
        auxRev.bucketSort();
        tfim = System.currentTimeMillis();
        RevTot = (tfim-tini)/1000; revMov = auxRev.getMov(); revComp = auxRev.getComp();
        revCompEqua = 0;
        revMovEqua = 0;

        //Arquivo randômico
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        tini = System.currentTimeMillis();
        auxRand.bucketSort();
        tfim = System.currentTimeMillis();
        RandTot = (tfim-tini)/1000; randMov = auxRand.getMov(); randComp = auxRand.getComp();
        randCompEqua = 0;
        randMovEqua = 0;

        gravaLinhaTabela("Bucket", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot,
                                                revComp, revCompEqua, revMov, revMovEqua, RevTot,
                                                randComp, randCompEqua, randMov, randMovEqua, RandTot);

        //----------------------
        //14 - Radix Sort
        //----------------------
        //Arquivo ordenado
        auxOrd.copiaArquivo(arqOrd);
        auxOrd.initComp();
        auxOrd.initMov();
        tini = System.currentTimeMillis();
        auxOrd.radixSort();
        tfim = System.currentTimeMillis();
        OrdTot = (tfim-tini)/1000; ordMov = auxOrd.getMov(); ordComp = auxOrd.getComp();
        ordCompEqua = 0;
        ordMovEqua = 0;
        
        //Arquivo reverso
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        tini = System.currentTimeMillis();
        auxRev.radixSort();
        tfim = System.currentTimeMillis();
        RevTot = (tfim-tini)/1000; revMov = auxRev.getMov(); revComp = auxRev.getComp();
        revCompEqua = 0;
        revMovEqua = 0;

        //Arquivo randômico
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        tini = System.currentTimeMillis();
        auxRand.radixSort();
        tfim = System.currentTimeMillis();
        RandTot = (tfim-tini)/1000; randMov = auxRand.getMov(); randComp = auxRand.getComp();
        randCompEqua = 0;
        randMovEqua = 0;

        gravaLinhaTabela("Radix", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot,
                                                revComp, revCompEqua, revMov, revMovEqua, RevTot,
                                                randComp, randCompEqua, randMov, randMovEqua, RandTot);

        //----------------------
        //15 - Comb Sort
        //----------------------
        //Arquivo ordenado
        auxOrd.copiaArquivo(arqOrd);
        auxOrd.initComp();
        auxOrd.initMov();
        tini = System.currentTimeMillis();
        auxOrd.CombSort();
        tfim = System.currentTimeMillis();
        OrdTot = (tfim-tini)/1000; ordMov = auxOrd.getMov(); ordComp = auxOrd.getComp();
        ordCompEqua = 0;
        ordMovEqua = 0;
        
        //Arquivo reverso
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        tini = System.currentTimeMillis();
        auxRev.CombSort();
        tfim = System.currentTimeMillis();
        RevTot = (tfim-tini)/1000; revMov = auxRev.getMov(); revComp = auxRev.getComp();
        revCompEqua = 0;
        revMovEqua = 0;

        //Arquivo randômico
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        tini = System.currentTimeMillis();
        auxRand.CombSort();
        tfim = System.currentTimeMillis();
        RandTot = (tfim-tini)/1000; randMov = auxRand.getMov(); randComp = auxRand.getComp();
        randCompEqua = 0;
        randMovEqua = 0;

        gravaLinhaTabela("Comb", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot,
                                                revComp, revCompEqua, revMov, revMovEqua, RevTot,
                                                randComp, randCompEqua, randMov, randMovEqua, RandTot);

        //----------------------
        //16 - Gnome Sort
        //----------------------
        //Arquivo ordenado
        auxOrd.copiaArquivo(arqOrd);
        auxOrd.initComp();
        auxOrd.initMov();
        tini = System.currentTimeMillis();
        auxOrd.gnomeSort();
        tfim = System.currentTimeMillis();
        OrdTot = (tfim-tini)/1000; ordMov = auxOrd.getMov(); ordComp = auxOrd.getComp();
        ordCompEqua = 0;
        ordMovEqua = 0;
        
        //Arquivo reverso
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        tini = System.currentTimeMillis();
        auxRev.gnomeSort();
        tfim = System.currentTimeMillis();
        RevTot = (tfim-tini)/1000; revMov = auxRev.getMov(); revComp = auxRev.getComp();
        revCompEqua = 0;
        revMovEqua = 0;

        //Arquivo randômico
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        tini = System.currentTimeMillis();
        auxRand.gnomeSort();
        tfim = System.currentTimeMillis();
        RandTot = (tfim-tini)/1000; randMov = auxRand.getMov(); randComp = auxRand.getComp();
        randCompEqua = 0;
        randMovEqua = 0;

        gravaLinhaTabela("Gnome", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot,
                                                revComp, revCompEqua, revMov, revMovEqua, RevTot,
                                                randComp, randCompEqua, randMov, randMovEqua, RandTot);

        //----------------------
        //17 - Tim Sort
        //----------------------
        //Arquivo ordenado
        auxOrd.copiaArquivo(arqOrd);
        auxOrd.initComp();
        auxOrd.initMov();
        tini = System.currentTimeMillis();
        auxOrd.timSort();
        tfim = System.currentTimeMillis();
        OrdTot = (tfim-tini)/1000; ordMov = auxOrd.getMov(); ordComp = auxOrd.getComp();
        ordCompEqua = 0;
        ordMovEqua = 0;
        
        //Arquivo reverso
        auxRev.copiaArquivo(arqRev);
        auxRev.initComp();
        auxRev.initMov();
        tini = System.currentTimeMillis();
        auxRev.timSort();
        tfim = System.currentTimeMillis();
        RevTot = (tfim-tini)/1000; revMov = auxRev.getMov(); revComp = auxRev.getComp();
        revCompEqua = 0;
        revMovEqua = 0;

        //Arquivo randômico
        auxRand.copiaArquivo(arqRand);
        auxRand.initComp();
        auxRand.initMov();
        tini = System.currentTimeMillis();
        auxRand.timSort();
        tfim = System.currentTimeMillis();
        RandTot = (tfim-tini)/1000; randMov = auxRand.getMov(); randComp = auxRand.getComp();
        randCompEqua = 0;
        randMovEqua = 0;

        gravaLinhaTabela("Tim", ordComp, ordCompEqua, ordMov, ordMovEqua, OrdTot,
                                                revComp, revCompEqua, revMov, revMovEqua, RevTot,
                                                randComp, randCompEqua, randMov, randMovEqua, RandTot);
    }
}