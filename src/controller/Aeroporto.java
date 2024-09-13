package controller;

import java.util.concurrent.Semaphore;

/*
 * Um aeroporto tem duas pistas
 * pista norte, pista sul
 * A decolagem tem 4 fases[]: {manobrar, taxiar, decolagem, afastamentoArea) 
 * A manobra dura entre 300 e 700 milissegundos
 * A fase de taxiar dura entre 500 a 1000 milissegundos
 * A fase de decolagem dura entre 600 e 800 milissegundos
 * A fase de afastamento dura entre 300 a 800 milissegundos
 * O aeroporto reúne 12 aeronaves que podem decolar por ciclo.
 * 
 * @author Daiane Tararam
 * @date 12 de setembro de 2024
 */
public class Aeroporto extends Thread {
	private static Semaphore pistaNorte = new Semaphore(1);
	private static Semaphore pistaSul = new Semaphore(1);
	private static Semaphore areaDecolagem = new Semaphore(2);
	
	private int aeronave;

	public Aeroporto(int aeronave) {
		this.aeronave = aeronave;
	}

	@Override
	public void run() {
		iniciarVoo();
	}

	private void iniciarVoo() {
		Semaphore pista = Math.random() > 0.5 ? pistaSul : pistaNorte;
		try {
			pista.acquire();
			
			System.out.println("A aeronave "+ aeronave + " já está na pista" + (pista == pistaNorte ? "Norte": "Sul"));
			
			int tempoManobra = (int) ((Math.random() * (701 - 300)) + 300);
			System.out.println("A aeronave " + aeronave + " levou " + tempoManobra + "ms. para manobrar.");
			Thread.sleep(tempoManobra);
			
			int tempoTaxiar = (int) ((Math.random() * (1001 - 500)) + 500);
			System.out.println("A aeronave " + aeronave + " levou " + tempoTaxiar + ("ms. para taxiar e já está preparada para decolar."));
			Thread.sleep(tempoTaxiar);
			
			areaDecolagem.acquire();
			int tempoDecolar = (int) ((Math.random() * (801 - 600)) + 600);
			System.out.println("Aeronave: " + aeronave + " DECOLANDO...");
			System.out.println("A aeronave "+aeronave+ " levou " + tempoDecolar + "ms. para decolar.");
			Thread.sleep(tempoDecolar);
			
			int tempoAfastamento = (int) ((Math.random() * (801 - 300)) + 300);
			System.out.println("A aeronave " +aeronave + " se afastou em " + tempoAfastamento + "ms.");
			Thread.sleep(tempoAfastamento);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			areaDecolagem.release();
			pista.release();
		}

	}
}
