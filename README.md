# Custom Fuel

Server-side Minecraft Fabric mod that lets you configure which items can be
used as furnace fuel.

The settings are stored in `custom_fuel.json` in the config folder.

## Config file format

A valid config file consists of two optional entries on the top level -
`include` and `exclude`.
- `include` is a map where the keys are IDs for the items and/or item tags to
  be added to the list of fuel items, and their values are how long they last
  (in ticks, as an integer).
  - If a value is zero or negative, the item can be placed into the fuel slot,
    but will not burn. This behaviour relies on vanilla code, and may change at
    any point in the future.
- `exclude` is an array of IDs for the item *tags* to no longer be usable as
  fuel items.

The mod applies the inclusions, then the exclusions, *on top of* the vanilla
fuel list (i.e. vanilla fuel items are kept intact unless you manually exclude
them).

A current limitation of this mod is that exclusions must be tags. To exclude
individual items, you have to make a datapack with your own custom tag that
contains all the individual items you want to exclude, and exclude that tag.

## Example configuration

```json
{
	"include": {
		"#c:netherracks": 3200,
		"#minecraft:beds": 300,
		"#minecraft:flowers": 50,
		"minecraft:redstone_block": 16000
	},
	"exclude": [
		"#minecraft:banners"
	]
}
```
