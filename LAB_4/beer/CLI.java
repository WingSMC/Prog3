package beer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.Scanner;
import java.util.Comparator;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Arrays;
import java.util.Queue;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class CLI {
	protected List<Beer> beers = new ArrayList<>();
	protected Map<String, Command> commands = new HashMap<>();
	boolean isRunning = false;

	public void run() {
		this.isRunning = true;
		System.out.println("--- Beer management system ---");
		Scanner s = new Scanner(System.in);

		while(this.isRunning) {
			System.out.print("> ");
			Queue<String> args = new LinkedList<>(Arrays.asList(s.nextLine().split(" ")));

			Command command = commands.get(args.poll());
			if(command == null) {
				System.out.println("That is an unknown command.");
				continue;
			}
			command.run(this.beers, args);
		}
		s.close();
	}

	public CLI() {
		commands.put("add", (List<Beer> beers, Queue<String> args) -> {
			if(args.size() < 1) {
				System.out.println("Too few arguments!");
				return;
			}
			beers.add(new Beer(
				args.poll(),
				args.poll(),
				Double.parseDouble(args.poll())
			));
		});
		commands.put("delete", (List<Beer> beers, Queue<String> args) -> {
			if(args.size() < 3) {
				System.out.println("Too few arguments!");
				return;
			}
			String name = args.poll();
			Iterator<Beer> beerterator = beers.iterator();
			while(beerterator.hasNext()) {
				if(beerterator.next().name().equals(name)) {
					beerterator.remove();
					return;
				}
			}
		});
		commands.put("list", (List<Beer> beers, Queue<String> args) -> {
			if(args.size() > 0) {
				Comparator<Beer> comparator;
				switch(args.poll()) {
					case "name":
						comparator = (Beer b1, Beer b2) -> b1.name().compareTo(b2.name());
						break;
					case "style":
						comparator = (Beer b1, Beer b2) -> b1.style().compareTo(b2.style());
						break;
					case "strength":
						comparator = (Beer b1, Beer b2) -> Double.compare(b1.strength(), b2.strength());
						break;
					default:
						System.out.println("Invalid argument!");
						return;
				}
				beers.sort(comparator);
			}
			printBeerList(beers);
		});
		commands.put("search", (List<Beer> beers, Queue<String> args) -> {
			if(args.size() < 1) {
				System.out.println("Too few arguments!");
				return;
			}
			String name = args.poll();
			List<Beer> filtered = new ArrayList<>();
			beers.forEach(beer -> {
				if(beer.name().equals(name))
					filtered.add(beer);
			});
			printBeerList(filtered);
		});
		commands.put("find", (List<Beer> beers, Queue<String> args) -> {
			if(args.size() < 1) {
				System.out.println("Too few arguments!");
				return;
			}
			String regex = args.poll();
			List<Beer> filtered = new ArrayList<>();
			beers.forEach(beer -> {
				if(beer.name().matches(regex))
					filtered.add(beer);
			});
			printBeerList(filtered);
		});
		commands.put("load", (List<Beer> beers, Queue<String> args) -> {
			if(args.size() < 1) {
				System.out.println("Too few arguments!");
				return;
			}
			try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(args.poll()))) {
				List<Beer> readBeers = (List<Beer>) in.readObject();
				beers.clear();
				beers.addAll(readBeers);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		commands.put("save", (List<Beer> beers, Queue<String> args) -> {
			if(args.size() < 1) {
				System.out.println("Too few arguments!");
				return;
			}
			try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(args.poll()))) {
				out.writeObject(beers);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		commands.put("exit", (List<Beer> beers, Queue<String> args) -> {
			isRunning = false;
			System.out.println("Exiting...");
		});
	}

	protected void printBeerList(List<Beer> beers) {
		beers.forEach(b -> System.out.println(b));
	}
}
