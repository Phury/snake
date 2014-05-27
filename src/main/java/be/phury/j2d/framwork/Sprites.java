package be.phury.j2d.framwork;

import java.awt.Graphics2D;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Sprites<T extends Sprite> implements Iterable<T> {

	private List<T> tiles = new LinkedList<>();
	
	public void updateAll(double dt) {
		for (Sprite t : tiles) {
			t.update(dt);
		}
	}

	public void renderAll(Graphics2D g) {
		for (Sprite t : tiles) {
			t.render(g);
		}
	}

	public void add(T tile) {
		tiles.add(tile);
	}

	@Override
	public Iterator<T> iterator() {
		return tiles.iterator();
	}

	public Sprites<T> removeAll(Sprites<? extends Box> toRemove) {
		tiles.removeAll(toRemove.tiles);
		return this;
	}

	@SuppressWarnings("unchecked")
	public Sprites<T> addAll(Sprites<? extends Box> toAdd) {
		tiles.addAll((Collection<? extends T>) toAdd.tiles);
		return this;
	}

	public Sprites<T> clear() {
		tiles.clear();
		return this;
	}
}
