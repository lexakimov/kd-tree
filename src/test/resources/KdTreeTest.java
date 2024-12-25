import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KdTreeTest {
	
	@Test
	void testWikipedia() {
		double[][] coords = {{2, 3}, {5, 4}, {9, 6}, {4, 7}, {8, 1}, {7, 2}};
		List<KdTree.Node> nodes = new ArrayList<>();
		for (int i = 0; i < coords.length; ++i) {
			nodes.add(new KdTree.Node(coords[i]));
		}
		var tree = new KdTree(2, nodes);
		var nearest = tree.findNearest(new KdTree.Node(9, 2));
		System.out.println("Wikipedia example data:");
		System.out.println("nearest point: " + nearest);
		System.out.println("distance: " + tree.distance());
		System.out.println("nodes visited: " + tree.visited());
	}
	
	@Test
	void testRandom1000() {
		testRandom(1000);
	}
	
	@Test
	void testRandom1_000_000() {
		testRandom(1_000_000);
	}
	
	@Test
	void testRandom1_000_000_000() {
		testRandom(2_000_000);
	}
	
	void testRandom(int points) {
		var random = new Random();
		
		List<KdTree.Node> nodes = new ArrayList<>();
		for (int i = 0; i < points; ++i)
			nodes.add(randomPoint(random));
		
		var tree = new KdTree(3, nodes);
		var target = randomPoint(random);
		var nearest = tree.findNearest(target);
		
		System.out.println("Random data (" + points + " points):");
		System.out.println("target: " + target);
		System.out.println("nearest point: " + nearest);
		System.out.println("distance: " + tree.distance());
		System.out.println("nodes visited: " + tree.visited());
	}
	
	KdTree.Node randomPoint(Random random) {
		double x = random.nextDouble();
		double y = random.nextDouble();
		double z = random.nextDouble();
		return new KdTree.Node(x, y, z);
	}
}
